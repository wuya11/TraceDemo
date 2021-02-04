package com.trace.base.tool.annotation;

import java.lang.annotation.*;

/**
 * 记录feign发起请求时的记录信息
 *
 * @author wl 2021-2-3
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FeignSpan {
}
