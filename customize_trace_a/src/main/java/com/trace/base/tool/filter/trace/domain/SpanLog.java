package com.trace.base.tool.filter.trace.domain;

import java.util.List;

/**
 * span信息日志
 *
 * @author ty
 */
public class SpanLog {
    /**
     * 请求链唯一编号,可以外联获取整个请求链的请求和响应数据
     */
    private String traceId;
    /**
     * 当前请求唯一编号,可以外联获取当前请求的请求和响应数据
     */
    private String requestId;
    /**
     * 当前接受请求span,作为父span
     */
    private Span span;
    /**
     * 请求路径
     */
    private String requestUri;
    /**
     * 请求方法
     */
    private String requestMethod;
    /**
     * 子span列表
     */
    private List<Span> subSpans;

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

    public Span getSpan() {
        return span;
    }

    public void setSpan(Span span) {
        this.span = span;
    }

    public List<Span> getSubSpans() {
        return subSpans;
    }

    public void setSubSpans(List<Span> subSpans) {
        this.subSpans = subSpans;
    }

    public String getRequestUri() {
        return requestUri;
    }

    public void setRequestUri(String requestUri) {
        this.requestUri = requestUri;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    @Override
    public String toString() {
        return "SpanLog{" +
                "traceId='" + traceId + '\'' +
                ", requestId='" + requestId + '\'' +
                ", span=" + span +
                ", requestUri='" + requestUri + '\'' +
                ", requestMethod='" + requestMethod + '\'' +
                ", subSpans=" + subSpans +
                '}';
    }
}
