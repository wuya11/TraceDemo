package com.trace.base.tool.feign.test;

import com.trace.base.tool.annotation.FeignSpan;
import com.trace.base.tool.feign.test.impl.TestFeignImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * feign测试
 *
 * @author wl
 */
@FeignClient(name = "feign-test", url = "http://127.0.0.1:8096/customize-trace-B", fallbackFactory = TestFeignImpl.class)
public interface TestFeign {


    /**
     * 获取服务名称
     *
     * @return 服务名称
     */
    @FeignSpan
    @RequestMapping(value = "trace/name", method = RequestMethod.GET)
    String getServiceNameB();

    /**
     * 测试线程池
     *
     */
    @RequestMapping(value = "trace/test", method = RequestMethod.GET)
    String testAsync();

}

