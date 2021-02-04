package com.trace.base.tool.filter.trace.domain;


import java.io.Serializable;

/**
 * 服务追踪信息
 *
 * @author ty
 */
public class Trace implements Serializable {
    /**
     * 全局追踪编号
     */
    private String traceId;
    /**
     * 当前请求编号
     */
    private String requestId;

    public Trace() {

    }

    public Trace(String traceId, String requestId) {
        this.traceId = traceId;
        this.requestId = requestId;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    @Override
    public String toString() {
        return "Trace{" +
                "traceId='" + traceId + '\'' +
                ", requestId='" + requestId + '\'' +
                '}';
    }
}
