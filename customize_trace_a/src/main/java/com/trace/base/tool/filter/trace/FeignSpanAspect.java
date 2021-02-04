package com.trace.base.tool.filter.trace;

import com.trace.base.tool.filter.trace.domain.Annotation;
import com.trace.base.tool.filter.trace.domain.Span;
import com.trace.base.tool.util.ThreadHolderUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.trace.base.tool.filter.trace.TraceFilter.PARENT_SPAN_ID_KEY;
import static com.trace.base.tool.filter.trace.TraceFilter.SUB_SPAN_LIST_KEY;

/**
 * 用于支持Feign请求记录
 *
 * @author wl
 */
@Aspect
@Component
public class FeignSpanAspect {

    @Pointcut("@annotation(com.trace.base.tool.annotation.FeignSpan)")
    public void pointcut() {
    }

    @Around("pointcut()")
    public void around(ProceedingJoinPoint joinPoint) {
        try {
            // 先生成spanId
            String spanId = TraceContext.parentSpanId();
            ThreadHolderUtil.setValue("feign-spanId", spanId);
            // cs
            Annotation cs = TraceContext.cs();
            List<Annotation> annotations = new ArrayList<>(2);
            annotations.add(cs);
            // 避免执行超时,所以先设置span cs信息
            Span span = new Span.Builder()
                    .parentId(ThreadHolderUtil.getValue(PARENT_SPAN_ID_KEY, String.class))
                    .spanId(spanId)
                    .annotations(annotations)
                    .build();
            List<Span> subSpanList = ThreadHolderUtil.getValue(SUB_SPAN_LIST_KEY, List.class);
            if (subSpanList != null) {
                subSpanList.add(span);
            }

            joinPoint.proceed();
            // cr
            Annotation cr = TraceContext.cr();
            // 增加cr
            annotations.add(cr);
            span.setDuration(cr.getTimestamp() - cs.getTimestamp());
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

}
