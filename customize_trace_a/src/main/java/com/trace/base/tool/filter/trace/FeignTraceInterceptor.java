package com.trace.base.tool.filter.trace;

import com.trace.base.tool.util.ThreadHolderUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.Logger;
import org.springframework.util.StringUtils;

import static com.trace.base.tool.filter.trace.TraceContext.PARENT_ID_HEADER;
import static com.trace.base.tool.util.StringConst.*;


/**
 * 调用服务追踪信息feign拦截器
 *
 * @author ty
 */
public class FeignTraceInterceptor implements RequestInterceptor {
    private static final Logger LOGGER = LoggerUtil.getTraceLogger();

    @Override
    public void apply(RequestTemplate template) {
        String projectName = LoggerUtil.PROJECT_NAME;
        if (!StringUtils.isEmpty(projectName)) {
            template.header(REFER_SERVICE_NAME, projectName);
            LOGGER.debug("feign header " + REFER_SERVICE_NAME + "=" + projectName);
        }
        if (!StringUtils.isEmpty(HOST_IP)) {
            template.header(REFER_REQUEST_HOST, HOST_IP);
            LOGGER.debug("feign header " + REFER_REQUEST_HOST + "=" + HOST_IP);
        }
        String traceId = TraceUtil.getTraceId();
        if (StringUtils.isEmpty(traceId)) {
            traceId = LoggerUtil.traceId();
        }
        template.header(GATEWAY_TRACE, traceId);
        String spanId = ThreadHolderUtil.getValue("feign-spanId", String.class);
        template.header(PARENT_ID_HEADER, spanId);
        LOGGER.debug("feign header " + GATEWAY_TRACE + "=" + traceId);
    }
}
