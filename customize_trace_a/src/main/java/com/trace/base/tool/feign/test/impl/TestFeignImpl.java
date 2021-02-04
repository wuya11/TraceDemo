package com.trace.base.tool.feign.test.impl;

import com.trace.base.tool.feign.test.TestFeign;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * 回滚类
 *
 * @author wl
 */
@Component
public class TestFeignImpl implements FallbackFactory<TestFeign> {


    @Override
    public TestFeign create(Throwable cause) {
        return new TestFeign() {

            @Override
            public String getServiceNameB() {
                return "feign 调用失败" + cause;
            }

            @Override
            public String testAsync() {
                return "feign 调用失败" + cause;
            }
        };
    }


}
