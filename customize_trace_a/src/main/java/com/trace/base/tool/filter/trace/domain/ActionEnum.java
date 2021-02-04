package com.trace.base.tool.filter.trace.domain;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 类型枚举
 *
 * @author ty
 */
public enum ActionEnum {
    /**
     * 客户端发起请求
     */
    CLIENT_SEND("cs"),
    /**
     * 服务器接受请求
     */
    SERVER_RECEIVE("sr"),
    /**
     * 服务端发起响应
     */
    SEVER_SEND("ss"),
    /**
     * 客户端接受响应
     */
    CLIENT_RECEIVE("cr");
    /**
     * 名称
     */
    private final String name;

    ActionEnum(String name) {
        this.name = name;
    }

    @JsonValue
    public String getName() {
        return name;
    }
}
