package com.gradleproject.service;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import java.time.LocalDateTime;
import java.util.UUID;

@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST,
        proxyMode = org.springframework.context.annotation.ScopedProxyMode.TARGET_CLASS)
public class RequestScopedBean {

    private final String requestId;
    private final LocalDateTime requestTime;

    public RequestScopedBean() {
        this.requestId = UUID.randomUUID().toString();
        this.requestTime = LocalDateTime.now();
    }

    /**
     * Generates a trace message including a unique request ID and the time of the request.
     */
    public String getRequestTrace(String details) {
        return "Request [" + requestId + "] at " + requestTime + " - Details: " + details;
    }

    public String getRequestId() {
        return requestId;
    }

    public LocalDateTime getRequestTime() {
        return requestTime;
    }
}