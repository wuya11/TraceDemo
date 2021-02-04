package com.trace.base.tool.util.log;

/**
 * 日志级别枚举
 *
 * @author ty
 */
public enum LevelEnum {
    /**
     * DEBUG
     */
    DEBUG(100, "DEBUG"),
    /**
     * INFO
     */
    INFO(200, "INFO"),
    /**
     * NOTICE
     */
    NOTICE(250, "NOTICE"),
    /**
     * WARNING
     */
    WARNING(300, "WARNING"),
    /**
     * ERROR
     */
    ERROR(400, "ERROR"),
    /**
     * CRITICAL
     */
    CRITICAL(500, "CRITICAL"),
    /**
     * ALERT
     */
    ALERT(550, "ALERT"),
    /**
     * EMERGENCY
     */
    EMERGENCY(600, "EMERGENCY");
    /**
     * 级别数字
     */
    private final int level;
    /**
     * 级别名称
     */
    private final String levelName;

    LevelEnum(int level, String levelName) {
        this.level = level;
        this.levelName = levelName;
    }

    public int getLevel() {
        return level;
    }

    public String getLevelName() {
        return levelName;
    }
}
