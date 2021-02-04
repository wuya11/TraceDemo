package com.trace.base.tool.filter.trace;

import com.trace.base.tool.filter.WebUtil;
import com.trace.base.tool.filter.trace.domain.*;
import com.trace.base.tool.util.LocalDateTimeUtil;
import com.trace.base.tool.util.ObjectMapperUtil;
import com.trace.base.tool.util.StringConst;
import com.trace.base.tool.util.ThreadHolderUtil;
import com.trace.base.tool.util.log.BaseLog;
import com.trace.base.tool.util.log.Channel;
import com.trace.base.tool.util.log.LevelEnum;
import org.slf4j.Logger;
import org.springframework.http.HttpMethod;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.trace.base.tool.util.StringConst.GATEWAY_TRACE;
import static com.trace.base.tool.util.StringConst.REFER_SERVICE_NAME;
import static com.trace.base.tool.util.ThreadHolderUtil.TRACE_KEY;

/**
 * 服务调用追踪过滤器
 *
 * @author ty
 */
public class TraceFilter implements Filter {
    private static final Logger TRACE_LOGGER = LoggerUtil.getTraceLogger();
    private static final Logger SPAN_LOGGER = LoggerUtil.getSpanLogger();
    private static final int DEFAULT_CONTENT_LOG_MAX = 5000;
    static final String PARENT_SPAN_ID_KEY = "parentSpanId";
    static final String SUB_SPAN_LIST_KEY = "subSpanList";
    /**
     * 为避免客户端自定义过滤器耗时被遗漏,建议优先级必须低于此值(即数值大于此值),跨域过滤器除外(优先级设置为0,优先级几乎高于绝大部分)
     */
    public static final int CUSTOM_FILTER_MAX_ORDER = 100;
    /**
     * 可配置的被忽略路径
     */
    private final String[] ignorePath;
    private final int contentLogMax;

    TraceFilter(String[] ignorePath, int contentLogMax) {
        this.ignorePath = ignorePath;
        this.contentLogMax = contentLogMax > 0 ? contentLogMax : DEFAULT_CONTENT_LOG_MAX;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        String uri = servletRequest.getRequestURI();
        // 服务健康检查日志不统计,根目录和HEAD请求忽略
        final String slash = "/";
        if (Arrays.stream(ignorePath).anyMatch(uri::startsWith) || slash.equals(uri) || HttpMethod.HEAD.name().equalsIgnoreCase(servletRequest.getMethod())) {
            chain.doFilter(request, response);
        } else {
            try {
                String ip = StringConst.HOST_IP;
                int port = request.getLocalPort();
                TraceLog traceLog = new TraceLog();
                traceLog.setRequestTime(getNowUs());
                traceLog.setServiceName(LoggerUtil.PROJECT_NAME);
                // 开始时间戳(微秒)
                long start = LocalDateTimeUtil.getCurrentMicroSecond();
                // traceId
                String traceId = servletRequest.getHeader(GATEWAY_TRACE);
                if (StringUtils.isEmpty(traceId)) {
                    traceId = LoggerUtil.traceId();
                }
                // 尝试获取上游传递的parent_id
                String parentId = servletRequest.getHeader(TraceContext.PARENT_ID_HEADER);
                // 首先设置span id,作为后续子span的父span id
                String spanId = TraceContext.parentSpanId();
                ThreadHolderUtil.setValue(PARENT_SPAN_ID_KEY, spanId);
                // 需要提前初始化子span列表,否则父子线程无法持有一个数据引用
                ThreadHolderUtil.setValue(SUB_SPAN_LIST_KEY, new ArrayList<>());

                // sr
                Annotation sr = TraceContext.sr();
                String requestId = LoggerUtil.requestId();
                // 设置trace,用于ResponseBody能够获取
                Trace trace = new Trace(traceId, requestId);
                ThreadHolderUtil.setValue(TRACE_KEY, trace);

                traceLog.setTraceId(traceId);
                // 2019/1/11新增requestId
                traceLog.setRequestId(requestId);
                traceLog.setReferServiceName(servletRequest.getHeader(REFER_SERVICE_NAME));
                try {
                    traceLog.setReferServiceHost(WebUtil.getRemoteIp());
                } catch (Exception ex) {
                    // 忽略
                }
                traceLog.setRequestUri(servletRequest.getRequestURI());
                String method = servletRequest.getMethod();
                traceLog.setRequestMethod(method);
                traceLog.setServiceAddr(ip);
                traceLog.setServicePort(port);

                // 原始response对象
                chain.doFilter(request, response);
                // 结束时间戳(微秒)
                long end = LocalDateTimeUtil.getCurrentMicroSecond();
                // ss
                Annotation ss = TraceContext.ss();
                // duration
                long duration = ss.getTimestamp() - sr.getTimestamp();
                // span日志
                SpanLog spanLog = new SpanLog();
                // 父span
                Span span = new Span.Builder()
                        .parentId(parentId)
                        .spanId(spanId)
                        .duration(duration)
                        .annotations(Arrays.asList(sr, ss))
                        .build();
                spanLog.setTraceId(traceId);
                spanLog.setRequestId(requestId);
                spanLog.setSpan(span);
                List<Span> subSpanList = ThreadHolderUtil.getValue(SUB_SPAN_LIST_KEY, List.class);
                spanLog.setSubSpans(subSpanList);

                try {
                    BaseLog<SpanLog> spanBaseLog = new BaseLog<>();
                    spanBaseLog.setMessage("span log");
                    spanBaseLog.setLevel(LevelEnum.INFO.getLevel());
                    spanBaseLog.setLevelName(LevelEnum.INFO.getLevelName());
                    spanBaseLog.setChannel(Channel.SPAN);
                    spanBaseLog.setContext(spanLog);
                    spanBaseLog.setDatetime(LocalDateTimeUtil.getMicroSecondFormattedNow());
                    SPAN_LOGGER.info(ObjectMapperUtil.getSnakeObjectMapper().writeValueAsString(spanBaseLog));
                } catch (Throwable th) {
                    // ignore
                }
                traceLog.setResponseTime(getNowUs());
                traceLog.setTimeUsed(new BigDecimal(end - start).divide(new BigDecimal(1000), 3, BigDecimal.ROUND_HALF_UP).doubleValue());

                try {
                    BaseLog<TraceLog> traceBaseLog = new BaseLog<>();
                    traceBaseLog.setMessage("trace log");
                    traceBaseLog.setLevel(LevelEnum.INFO.getLevel());
                    traceBaseLog.setLevelName(LevelEnum.INFO.getLevelName());
                    traceBaseLog.setChannel(Channel.REQUEST);
                    traceBaseLog.setContext(traceLog);
                    traceBaseLog.setDatetime(LocalDateTimeUtil.getMicroSecondFormattedNow());
                    TRACE_LOGGER.info(ObjectMapperUtil.getSnakeObjectMapper().writeValueAsString(traceBaseLog));
                } catch (Throwable th) {
                    // ignore
                }

            } finally {
                // 最后清除VALUE_MAP
                ThreadHolderUtil.clearValueMap();
            }
        }
    }

    @Override
    public void destroy() {

    }

    /**
     * 获取当前时间戳(秒.微秒)
     */
    private double getNowUs() {
        long ms = System.currentTimeMillis();
        long ns = System.nanoTime();
        return new BigDecimal(ms * 1000 + ns % 1000000L / 1000).divide(new BigDecimal(1000000L), 6, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}