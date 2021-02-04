package com.trace.base.tool.filter;

import com.trace.base.tool.filter.trace.TraceFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 除框架外最前置的过滤器
 *
 * @author ty
 */
@Configuration
public class HeadFilterAutoConfiguration {
    @Bean
    public FilterRegistrationBean headFilter() {
        FilterRegistrationBean<HeadFilter> headFilterFilterRegistrationBean = new FilterRegistrationBean<>(new HeadFilter());
        headFilterFilterRegistrationBean.setName("headFilter");
        // 比TraceFilter优先级更高,保证在所有内部逻辑都处理完成后再清除
        headFilterFilterRegistrationBean.setOrder(TraceFilter.CUSTOM_FILTER_MAX_ORDER - 1);
        return headFilterFilterRegistrationBean;
    }
}
