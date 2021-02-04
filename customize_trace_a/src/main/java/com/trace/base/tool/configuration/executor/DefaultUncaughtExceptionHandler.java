package com.trace.base.tool.configuration.executor;

/**
 * 默认实现
 *
 * @author wl
 * @date 2021-2-4
 */
public class DefaultUncaughtExceptionHandler implements UncaughtExceptionHandler {
    @Override
    public void uncaughtException(Throwable e) {
        System.out.println("threadPool uncaughtException"+ e.toString());
    }
}