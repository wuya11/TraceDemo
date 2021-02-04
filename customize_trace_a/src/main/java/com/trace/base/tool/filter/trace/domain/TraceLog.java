package com.trace.base.tool.filter.trace.domain;

/**
 * 服务追踪日志
 *
 * @author ty
 */
public class TraceLog {
    /**
     * 服务追踪编号
     */
    private String traceId;
    /**
     * 请求路径
     */
    private String requestUri;
    /**
     * 请求方法
     */
    private String requestMethod;
    /**
     * 源服务名称
     */
    private String referServiceName;
    /**
     * 本服务名称
     */
    private String serviceName;
    /**
     * 源服务主机
     */
    private String referServiceHost;

    /**
     * 请求时间戳(秒.微秒)
     */
    private double requestTime;
    /**
     * 响应时间戳(秒.微秒)
     */
    private double responseTime;
    /**
     * 请求花费时间
     */
    private double timeUsed;

    /**
     * ip
     */
    private String serviceAddr;
    /**
     * 端口
     */
    private int servicePort;
    /**
     * 当前请求编号
     */
    private String requestId;

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
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

    public String getReferServiceName() {
        return referServiceName;
    }

    public void setReferServiceName(String referServiceName) {
        this.referServiceName = referServiceName;
    }

    public String getReferServiceHost() {
        return referServiceHost;
    }

    public void setReferServiceHost(String referServiceHost) {
        this.referServiceHost = referServiceHost;
    }

    public double getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(double responseTime) {
        this.responseTime = responseTime;
    }

    public double getTimeUsed() {
        return timeUsed;
    }

    public void setTimeUsed(double timeUsed) {
        this.timeUsed = timeUsed;
    }

    public String getServiceAddr() {
        return serviceAddr;
    }

    public void setServiceAddr(String serviceAddr) {
        this.serviceAddr = serviceAddr;
    }

    public int getServicePort() {
        return servicePort;
    }

    public void setServicePort(int servicePort) {
        this.servicePort = servicePort;
    }

    public double getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(double requestTime) {
        this.requestTime = requestTime;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    @Override
    public String toString() {
        return "TraceLog{" +
                "traceId='" + traceId + '\'' +
                ", requestUri='" + requestUri + '\'' +
                ", requestMethod='" + requestMethod + '\'' +
                ", referServiceName='" + referServiceName + '\'' +
                ", serviceName='" + serviceName + '\'' +
                ", referServiceHost='" + referServiceHost + '\'' +
                ", requestTime=" + requestTime +
                ", responseTime=" + responseTime +
                ", timeUsed=" + timeUsed +
                ", serviceAddr='" + serviceAddr + '\'' +
                ", servicePort=" + servicePort +
                ", requestId='" + requestId + '\'' +
                '}';
    }
}