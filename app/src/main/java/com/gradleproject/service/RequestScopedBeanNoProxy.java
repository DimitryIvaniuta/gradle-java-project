package com.gradleproject.service;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.util.UUID;

@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST)
public class RequestScopedBeanNoProxy {

    private final String requestId;

    public RequestScopedBeanNoProxy() {
        // Generate a unique ID for every HTTP request.
        this.requestId = UUID.randomUUID().toString();
    }

    public String getRequestId() {
        return requestId;
    }

}
