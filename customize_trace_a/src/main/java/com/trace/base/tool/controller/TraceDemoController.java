package com.trace.base.tool.controller;


import com.trace.base.tool.service.TraceDemoService;
import com.trace.base.tool.util.ThreadHolderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * 调用链测试demo
 *
 * @author wl
 * @date 2020-12-01
 */
@RestController
@RequestMapping("trace")
@Validated
public class TraceDemoController {

    @Autowired
    private TraceDemoService traceDemoService;

    /**
     * 获取当前服务名称
     *
     * @return 返回存储数据
     */
    @GetMapping("/name")
    public String getTraceService() {
        return traceDemoService.getTraceService();
    }

    /**
     * 获取远程服务名称
     *
     * @return 返回存储数据
     */
    @GetMapping("/feign/name")
    public String getTraceServiceB() {
        return traceDemoService.getServiceNameB();
    }

    /**
     * 线程池测试-jdk扩展
     *
     * @return 返回存储数据
     */
    @GetMapping("/jdk/async")
    public void testJdkAsync() {
        String traceid = Optional.ofNullable(ThreadHolderUtil.getTrace()).map(x -> x.getTraceId()).orElse("null");
        String message = "执行主线程：" + "traceid->" + traceid;
        System.out.println(message);
        traceDemoService.testJdkAsync();
    }

    /**
     * 线程池测试-decorator（装饰模式扩展）
     *
     * @return 返回存储数据
     */
    @GetMapping("/decorator/async")
    public void testDecoratorAsync() {
        String traceid = Optional.ofNullable(ThreadHolderUtil.getTrace()).map(x -> x.getTraceId()).orElse("null");
        String message = "执行主线程：" + "traceid->" + traceid;
        System.out.println(message);
        traceDemoService.testDecoratorAsync();
    }

    /**
     * 线程池测试-base基础模式
     *
     * @return 返回存储数据
     */
    @GetMapping("/base/async")
    public void testBaseAsync() {
        String traceid = Optional.ofNullable(ThreadHolderUtil.getTrace()).map(x -> x.getTraceId()).orElse("null");
        String message = "执行主线程：" + "traceid->" + traceid;
        System.out.println(message);
        traceDemoService.testBaseAsync();
    }
}
