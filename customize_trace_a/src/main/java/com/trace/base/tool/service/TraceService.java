package com.trace.base.tool.service;

/**
 * traceid
 *
 * @author wl
 * @date 2020-03-31
 */
public interface TraceService {

    /**
     * 异步线程池测试-jdk扩展增强
     *
     */
    void testJdkAsync(int i);

    /**
     * 异步线程池测试-装饰模式
     *
     */
    void testDecoratorAsync(int i);

    /**
     * 异步线程池测试-基础模式
     *
     */
    void testBaseAsync(int i);

}
