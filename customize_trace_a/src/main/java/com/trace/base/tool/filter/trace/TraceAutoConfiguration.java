package com.trace.base.tool.filter.trace;

import feign.Feign;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

/**
 * 服务追踪自动化配置
 *
 * @author ty
 */
@ConditionalOnProperty(prefix = "web", name = "trace", havingValue = "true")
@EnableConfigurationProperties(TraceProperties.class)
@Configuration
public class TraceAutoConfiguration {
    /**
     * 注入服务追踪过滤器
     */
    @Bean
    public FilterRegistrationBean traceFilter(TraceProperties traceProperties) {
        FilterRegistrationBean<TraceFilter> registrationBean = new FilterRegistrationBean<>(new TraceFilter(Optional.ofNullable(traceProperties.getIgnorePath()).orElse(new String[0]), traceProperties.getContentLogMax()));
        registrationBean.setName("traceFilter");
        /**
         * 为了保证不影响框架内部过滤器,凡是要重新包装REQUEST的filter顺序必须比下面的值等于或者小于
         * @see FilterRegistrationBean.REQUEST_WRAPPER_FILTER_MAX_ORDER
         */
        registrationBean.setOrder(TraceFilter.CUSTOM_FILTER_MAX_ORDER);
        return registrationBean;
    }

    @ConditionalOnClass(Feign.class)
    public static class FeignTraceAutoConfiguration {
        @Bean
        public FeignTraceInterceptor feignTraceInterceptor() {
            return new FeignTraceInterceptor();
        }
    }
}
