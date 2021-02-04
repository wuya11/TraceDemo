package com.trace.base.tool.filter.trace;


import com.trace.base.tool.filter.trace.domain.ActionEnum;
import com.trace.base.tool.filter.trace.domain.Annotation;
import com.trace.base.tool.util.LocalDateTimeUtil;

import java.util.Random;
import java.util.UUID;

/**
 * 服务追踪上下文
 *
 * @author ty
 */
public class TraceContext {
    /**
     * 父span id
     */
    final static String PARENT_ID_HEADER = "X-Parent-Id";

    /**
     * 子span列表
     */
     static String newSpanId() {
        return UUID.randomUUID().toString() + new Random().nextInt(1000000);
    }

     static String parentSpanId() {
        return newSpanId();
    }

    static Annotation sr() {
        return new Annotation.Builder()
                .action(ActionEnum.SERVER_RECEIVE)
                .timestamp(LocalDateTimeUtil.getCurrentMicroSecond())
                .build();
    }

    static Annotation ss() {
        return new Annotation.Builder()
                .action(ActionEnum.SEVER_SEND)
                .timestamp(LocalDateTimeUtil.getCurrentMicroSecond())
                .build();
    }

    static Annotation cs() {
        return new Annotation.Builder()
                .action(ActionEnum.CLIENT_SEND)
                .timestamp(LocalDateTimeUtil.getCurrentMicroSecond())
                .build();
    }

    static Annotation cr() {
        return new Annotation.Builder()
                .action(ActionEnum.CLIENT_RECEIVE)
                .timestamp(LocalDateTimeUtil.getCurrentMicroSecond())
                .build();
    }
}
