package com.trace.base.tool.util.log;

/**
 * 日志基础类
 *
 * @author ty
 */
public class BaseLog<T> {
    /**
     * 说明文本,也可以是日志内容索引Key
     */
    private String message;
    /**
     * 日志内容
     */
    private T context;
    /**
     * 日志级别
     */
    private int level;
    /**
     * 日志级别文本
     */
    private String levelName;
    /**
     * 日志业务类型
     */
    private String channel;
    /**
     * 时间,微秒单位
     */
    private String datetime;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getContext() {
        return context;
    }

    public void setContext(T context) {
        this.context = context;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    @Override
    public String toString() {
        return "BaseLog{" +
                "message='" + message + '\'' +
                ", context=" + context +
                ", level=" + level +
                ", levelName='" + levelName + '\'' +
                ", channel='" + channel + '\'' +
                ", datetime=" + datetime +
                '}';
    }
}
