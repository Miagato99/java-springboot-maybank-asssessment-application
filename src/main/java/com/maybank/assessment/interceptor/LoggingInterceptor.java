package com.maybank.assessment.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class LoggingInterceptor implements HandlerInterceptor {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        long startTime = System.currentTimeMillis();
        request.setAttribute("startTime", startTime);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        long startTime = (Long) request.getAttribute("startTime");
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        // Log request details
        logRequestDetails(request, duration);
        
        // Log response details
        logResponseDetails(response, duration);
    }

    private void logRequestDetails(HttpServletRequest request, long duration) {
        try {
            Map<String, Object> requestLog = new HashMap<>();
            requestLog.put("timestamp", LocalDateTime.now());
            requestLog.put("type", "REQUEST");
            requestLog.put("method", request.getMethod());
            requestLog.put("uri", request.getRequestURI());
            requestLog.put("queryString", request.getQueryString());
            requestLog.put("remoteAddr", request.getRemoteAddr());
            requestLog.put("headers", getHeaders(request));
            
            // Log request body if available
            if (request instanceof ContentCachingRequestWrapper) {
                ContentCachingRequestWrapper wrapper = (ContentCachingRequestWrapper) request;
                byte[] buf = wrapper.getContentAsByteArray();
                if (buf.length > 0) {
                    String payload = new String(buf, StandardCharsets.UTF_8);
                    requestLog.put("body", payload);
                }
            }
            
            log.info("REQUEST LOG: {}", objectMapper.writeValueAsString(requestLog));
        } catch (Exception e) {
            log.error("Error logging request", e);
        }
    }

    private void logResponseDetails(HttpServletResponse response, long duration) {
        try {
            Map<String, Object> responseLog = new HashMap<>();
            responseLog.put("timestamp", LocalDateTime.now());
            responseLog.put("type", "RESPONSE");
            responseLog.put("status", response.getStatus());
            responseLog.put("duration", duration + "ms");
            
            // Log response body if available
            if (response instanceof ContentCachingResponseWrapper) {
                ContentCachingResponseWrapper wrapper = (ContentCachingResponseWrapper) response;
                byte[] buf = wrapper.getContentAsByteArray();
                if (buf.length > 0) {
                    String payload = new String(buf, StandardCharsets.UTF_8);
                    responseLog.put("body", payload);
                }
            }
            
            log.info("RESPONSE LOG: {}", objectMapper.writeValueAsString(responseLog));
        } catch (Exception e) {
            log.error("Error logging response", e);
        }
    }

    private Map<String, String> getHeaders(HttpServletRequest request) {
        Map<String, String> headers = new HashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            headers.put(headerName, request.getHeader(headerName));
        }
        return headers;
    }
}
