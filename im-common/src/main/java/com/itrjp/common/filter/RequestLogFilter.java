package com.itrjp.common.filter;

import com.itrjp.common.trace.TraceUtil;
import com.itrjp.common.util.RequestUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * request log filter
 *
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2022/12/25 23:59
 */
@Slf4j
public class RequestLogFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, javax.servlet.FilterChain filterChain) throws ServletException, IOException {
        ContentCachingRequestWrapper cachingRequestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper cachingResponseWrapper = new ContentCachingResponseWrapper(response);
        long start = System.currentTimeMillis();
        Map<String, String> parameterMap = RequestUtils
                .getParamMap(request);
        String queryParam = request.getQueryString();
        String method = request.getMethod();

        filterChain.doFilter(request, cachingResponseWrapper);
        cachingResponseWrapper.setHeader(TraceUtil.HEADER_TRACE_ID, MDC.get("traceId"));

        int status = cachingResponseWrapper.getStatus();

        // 响应体
        String responseBody = new String(cachingResponseWrapper.getContentAsByteArray(), StandardCharsets.UTF_8);

        log.info("url: {}, method: {}, queryStr: {}, param: {}, response statusCode: {},  result: {}, duration: {}", request.getRequestURL(), method, queryParam, parameterMap, status, responseBody, System.currentTimeMillis() - start);

        cachingResponseWrapper.copyBodyToResponse();
    }
}
