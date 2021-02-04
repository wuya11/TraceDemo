package com.trace.base.tool.filter.trace;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Arrays;

/**
 * trace扩展属性
 *
 * @author ty
 */
@ConfigurationProperties(prefix = "web.trace")
public class TraceProperties {
    /**
     * 忽略的路径
     */
    private String[] ignorePath;
    private int contentLogMax;

    public String[] getIgnorePath() {
        return ignorePath;
    }

    public void setIgnorePath(String[] ignorePath) {
        this.ignorePath = ignorePath;
    }

    public int getContentLogMax() {
        return contentLogMax;
    }

    public void setContentLogMax(int contentLogMax) {
        this.contentLogMax = contentLogMax;
    }

    @Override
    public String toString() {
        return "TraceProperties{" +
                "ignorePath=" + Arrays.toString(ignorePath) +
                ", contentLogMax=" + contentLogMax +
                '}';
    }
}
