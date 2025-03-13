package com.gradleproject.service;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.util.UUID;

@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class RequestContext {

    private final String requestId;

    public RequestContext() {
        // Generate a unique request ID for every HTTP request
        this.requestId = UUID.randomUUID().toString();
    }

    public String getRequestId() {
        return requestId;
    }

}