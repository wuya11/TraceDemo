package com.trace.base.tool.util;

import static java.util.Optional.ofNullable;

/**
 * 常用的字符串常量
 *
 * @author ty
 */
public final class StringConst {
    /**
     * 网关解析token后传递给服务的用户信息header
     */
    public static final String GATEWAY_USER_HEADER = "X-Gateway-User";
    /**
     * 服务间相互调用传递的用户信息header
     */
    public static final String SERVICE_USER_HEADER = "X-Service-User";
    /**
     * 服务间相互调用传递的用户header(为了向后兼容,避免切换header时造成无法获取获取用户信息,任然传递这个header)
     */
    @Deprecated
    public static final String FEIGN_USER_HEADER = "X-Feign-User";
    /**
     * 服务调用源名称
     */
    public static final String REFER_SERVICE_NAME = "Refer-Service-Name";
    /**
     * 服务调用源主机
     */
    public static final String REFER_REQUEST_HOST = "Refer-Request-Host";
    /**
     * 网关追踪编号
     */
    public static final String GATEWAY_TRACE = "Gateway-Trace";
    /**
     * jwt bearer头
     */
    public static final String BEARER = "Bearer ";
    /**
     * authorization头
     */
    public static final String AUTHORIZATION = "Authorization";
    /**
     * 内网ip key
     */
    public static final String HOST_IP_KEY = "HOST_IP";
    /**
     * 内网ip
     */
    public static final String HOST_IP = ofNullable(System.getenv(HOST_IP_KEY)).orElse(System.getProperty(HOST_IP_KEY));
    /**
     * 项目名称key
     */
    public static final String PROJECT_NAME_KEY = "project.name";
    /**
     * 项目名称
     */
    public static final String PROJECT_NAME = ofNullable(System.getenv(PROJECT_NAME_KEY)).orElse(System.getProperty(PROJECT_NAME_KEY));
    /**
     * 环境Key
     */
    public static final String ENVIRONMENT_KEY = "ENVIRONMENT";
    /**
     * 环境
     */
    public static final String ENVIRONMENT = ofNullable(System.getenv(ENVIRONMENT_KEY)).orElse(System.getProperty(ENVIRONMENT_KEY));
    /**
     * k8s服务域名key
     */
    public static final String SERVICE_ADDR_KEY = "SERVICE_ADDR";
    /**
     * k8s服务域名
     */
    public static final String SERVICE_ADDR = ofNullable(System.getenv(SERVICE_ADDR_KEY)).orElse(System.getProperty(SERVICE_ADDR_KEY));
    /**
     * 是否k8s key
     */
    public static final String IS_K8S_KEY = "IS_K8S";
    /**
     * 是否k8s
     */
    public static final boolean IS_K8S = "1".equals(ofNullable(System.getenv(IS_K8S_KEY)).orElse(System.getProperty(IS_K8S_KEY)));
    /**
     * pod ip key
     */
    public static final String POD_IP_KEY = "POD_IP";
    /**
     * pod ip
     */
    public static final String POD_IP = ofNullable(System.getenv(POD_IP_KEY)).orElse(System.getProperty(POD_IP_KEY));
    /**
     * 开启bean延迟加载 key
     */
    public static final String LAZY_INITIALIZATION_KEY = "LAZY_INITIALIZATION";
    /**
     * 开启bean延迟加载
     */
    public static final boolean LAZY_INITIALIZATION = Boolean.parseBoolean(ofNullable(System.getenv(LAZY_INITIALIZATION_KEY)).orElse(System.getProperty(LAZY_INITIALIZATION_KEY)));
}

