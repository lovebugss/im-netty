package com.itrjp.common.util;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * RequestUtils
 *
 * @author renjp
 * @date 2022/4/27 23:13
 */
@Slf4j
public final class RequestUtils {
    private RequestUtils() {
    }

    /**
     * 检查参数是否存在
     *
     * @param request
     * @param fieldName
     * @return
     */
    public static boolean checkParamIsExist(HttpServletRequest request, String fieldName) {
        log.info("RequestUtils#checkParamIsExist, request: {}, field: {}", request, fieldName);
        if (request != null && fieldName != null) {
            Enumeration<String> parameterNames = request.getParameterNames();
            while (parameterNames.hasMoreElements()) {
                String nextElement = parameterNames.nextElement();
                if (nextElement.equals(fieldName)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static Map<String, String[]> getParams(ServletRequest request) {
        Map<String, String[]> result = Collections.unmodifiableMap(request.getParameterMap());
        log.info("RequestUtils#getParams, request: {}, params: {}", request, result);
        return result;
    }

    public static Map<String, String> getParamMap(ServletRequest request) {
        Map<String, String> params = new HashMap<>();
        for (Map.Entry<String, String[]> entry : getParams(request).entrySet()) {
            params.put(entry.getKey(), entry.getValue()[0]);
        }
        log.info("RequestUtils#getParamMap, request: {}, params: {}", request, params);

        return params;
    }

}
