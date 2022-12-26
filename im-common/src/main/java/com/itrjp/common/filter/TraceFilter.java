package com.itrjp.common.filter;

import com.itrjp.common.trace.TraceUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * 增加traceId
 *
 * @author renjp
 * @date 2022/4/27 23:59
 */
@Configuration
@WebFilter(value = "/**")
@Order(Integer.MIN_VALUE)
public class TraceFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            TraceUtil.addTraceId();
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            TraceUtil.clear();
        }
    }
}
