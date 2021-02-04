package com.trace.base.tool;

import com.trace.base.tool.util.StringConst;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author Administrator
 */
@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
@EnableAsync
public class TraceApplication {
    public static void main(String[] args) {
        System.out.println("trace-A-测试微服务开始启动...........");
        System.setProperty(StringConst.PROJECT_NAME_KEY, "customize-trace-A");
        new SpringApplicationBuilder(TraceApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
        System.out.println("trace-A-测试微服务开始完成............");
    }
}
