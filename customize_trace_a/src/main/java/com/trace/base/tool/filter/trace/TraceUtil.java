package com.trace.base.tool.filter.trace;


import com.trace.base.tool.filter.trace.domain.Trace;
import com.trace.base.tool.util.ThreadHolderUtil;

import java.util.Optional;

/**
 * trace工具类
 *
 * @author ty
 */
public final class TraceUtil {
    /**
     * 获取trace对象
     *
     * @return trace
     */
    public static Trace getTrace() {
        return ThreadHolderUtil.getValue(ThreadHolderUtil.TRACE_KEY, Trace.class);
    }

    /**
     * 获取traceId
     *
     * @return traceId
     */
    public static String getTraceId() {
        return Optional.ofNullable(getTrace()).map(Trace::getTraceId).orElse("");
    }

    /**
     * 获取requestId
     *
     * @return requestId
     */
    public static String requestId() {
        return Optional.ofNullable(getTrace()).map(Trace::getRequestId).orElse("");
    }
}
