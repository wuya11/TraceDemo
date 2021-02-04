package com.trace.base.tool.filter;

import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用于web项目的工具类
 *
 * @author ty
 */
public class WebUtil {
    /**
     * 获取当前request
     */
    public static HttpServletRequest getRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            throw new IllegalStateException("当前运行非servlet环境");
        }
        return attributes.getRequest();
    }

    /**
     * 获取当前response
     */
    public static HttpServletResponse getResponse() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            throw new IllegalStateException("当前运行非servlet环境");
        }
        return attributes.getResponse();
    }

    /**
     * 获取当前请求远程客户端ip
     */
    public static String getRemoteIp() {
        HttpServletRequest request = getRequest();
        String ip = request.getHeader("X-Forwarded-For");
        if (invalidIp(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (invalidIp(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (invalidIp(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (invalidIp(ip)) {
            ip = request.getRemoteAddr();
        }
        // 多次反向代理后会有多个ip，第一个为真实ip
        int index = ip.indexOf(",");
        if (index > -1) {
            ip = ip.substring(0, index);
        }
        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
    }

    /**
     * 检查ip字符串是否非法
     */
    private static boolean invalidIp(String ip) {
        return StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip);
    }
}
