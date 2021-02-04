package com.trace.base.tool.service;

/**
 * traceid
 *
 * @author wl
 * @date 2020-03-31
 */
public interface TraceDemoService {

    /**
     * 异步线程池测试
     *
     */
    void testJdkAsync();

    /**
     * 获取本服务的名称
     * @return 返回服务名称
     */
    String getTraceService();

    /**
     * 获取远程服务的名称-feign模式
     * @return 返回服务名称
     */
    String getServiceNameB();

    /**
     * 异步线程池测试-装饰器
     *
     */
    void testDecoratorAsync();

    /**
     * 异步线程池测试-基础模式
     *
     */
    void testBaseAsync();

}
