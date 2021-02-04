package com.trace.base.tool.service.impl;


import com.trace.base.tool.feign.test.TestFeign;
import com.trace.base.tool.service.TraceDemoService;
import com.trace.base.tool.service.TraceService;
import com.trace.base.tool.util.StringConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wl
 * @description trace test
 * @date 2020-03-31
 */
@Service
public class TraceDemoServiceImpl implements TraceDemoService {

    @Autowired
    private TraceService traceService;
    @Autowired
    private TestFeign testFeign;

    @Override
    public void testJdkAsync() {
        for (int i = 0; i < 20; i++) {
            traceService.testJdkAsync(i);
        }
    }

    @Override
    public void testDecoratorAsync() {
        for (int i = 0; i < 20; i++) {
            traceService.testDecoratorAsync(i);
        }
    }

    @Override
    public void testBaseAsync() {
        for (int i = 0; i < 20; i++) {
            traceService.testBaseAsync(i);
        }
    }

    @Override
    public String getTraceService() {
        return StringConst.PROJECT_NAME;
    }

    @Override
    public String getServiceNameB() {
        String firstAsk = "feign 模式远程调用微服务B:" + testFeign.getServiceNameB();
        return firstAsk;
    }
}
