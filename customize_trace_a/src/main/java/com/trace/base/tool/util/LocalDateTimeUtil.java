package com.trace.base.tool.util;

/**
 * 常量类
 *
 * @author wl
 */

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * java 8日期工具类
 *
 * @author ty
 */
public final class LocalDateTimeUtil {
    /**
     * 为了较精确获取微秒时间戳,通过计算nano和mill偏移量来得到nano时间戳,相对mill已经足够精确了
     */
    private static final long NANO_OFFSET = System.nanoTime() - System.currentTimeMillis() * 1000000L;
    /**
     * 开发中常用的日期格式化 yyyy-MM-dd
     */
    public static final DateTimeFormatter LOCAL_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    /**
     * 开发中常用的日期格式化 yyyy-MM-dd HH:mm:ss
     */
    public static final DateTimeFormatter LOCAL_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    /**
     * 开发中常用的日期格式化 yyyy-MM-dd HH:mm:ss SSS
     */
    public static final DateTimeFormatter MILLI_LOCAL_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss SSS");
    /**
     * 开发中常用的日期格式化 HH:mm:ss
     */
    public static final DateTimeFormatter LOCAL_TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    /**
     * 开发中常用的日期格式化 HH:mm:ss SSS
     */
    public static final DateTimeFormatter MILLI_LOCAL_TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss SSS");
    /**
     * 开发中常用的日期格式化 yyyy-MM
     */
    public static final DateTimeFormatter LOCAL_DATE_MONTH_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM");
    /**
     * 获取当前时间格式化字符串
     *
     * @return 当前时间格式化字符串
     */
    public static String getFormattedNow() {
        return LocalDateTime.now().format(LOCAL_DATE_TIME_FORMATTER);
    }
    /**
     * 获取当前时间毫秒格式化字符串
     *
     * @return 当前时间毫秒格式化字符串
     */
    public static String getMilliFormattedNow() {
        return LocalDateTime.now().format(MILLI_LOCAL_DATE_TIME_FORMATTER);
    }

    /**
     * 获取+8区日期时间
     *
     * @param second 秒
     * @return 日期时间
     */
    public static LocalDateTime ofEpochSecondPlus8(long second) {
        return LocalDateTime.ofEpochSecond(second, 0, ZoneOffset.of("+8"));
    }

    /**
     * 获取+8区日期时间
     *
     * @param milli 毫秒
     * @return 日期时间
     */
    public static LocalDateTime ofEpochMilliPlus8(long milli) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(milli), ZoneOffset.of("+8"));
    }

    /**
     * 格式化+8区日期时间字符串
     *
     * @param second 秒
     * @return 日期时间字符串
     */
    public static String formatPlus8(long second) {
        return LOCAL_DATE_TIME_FORMATTER.format(ofEpochSecondPlus8(second));
    }

    /**
     * 格式化+8区日期时间字符串
     *
     * @param milli 毫秒
     * @return 日期时间字符串
     */
    public static String formatMilliPlus8(long milli) {
        return MILLI_LOCAL_DATE_TIME_FORMATTER.format(ofEpochMilliPlus8(milli));
    }

    /**
     * 获取当前微秒时间戳
     *
     * @return 微秒
     */
    public static long getCurrentMicroSecond() {
        return (System.nanoTime() - NANO_OFFSET) / 1000L;
    }

    /**
     * 获取当前微秒格式化时间
     *
     * @return 微秒格式化时间
     */
    public static String getMicroSecondFormattedNow() {
        long micro = getCurrentMicroSecond();
        return formatPlus8(micro / 1000000L) + "." + micro % 1000000L;
    }
}
