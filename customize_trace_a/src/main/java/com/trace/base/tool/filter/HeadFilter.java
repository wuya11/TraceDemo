package com.trace.base.tool.filter;


import com.trace.base.tool.util.ThreadHolderUtil;

import javax.servlet.*;
import java.io.IOException;

/**
 * 首要执行的过滤器,统一用于清除ThreadHolderUtil中的数据
 *
 * @author ty
 */
public class HeadFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        try {
            chain.doFilter(servletRequest, servletResponse);
        } finally {
            ThreadHolderUtil.clearUser();
            ThreadHolderUtil.clearValueMap();
        }
    }

    @Override
    public void destroy() {

    }
}
