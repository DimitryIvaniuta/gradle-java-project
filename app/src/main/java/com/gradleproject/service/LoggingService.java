package com.gradleproject.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LoggingService {

    // The requestContext field is actually a proxy. Every call will delegate to the current request's instance.
    private final RequestContext requestContext;

    public LoggingService(RequestContext requestContext) {
        this.requestContext = requestContext;
    }

    /**
     * Logs a message along with the current request's ID.
     */
    public void log(String message) {
        String currentRequestId = requestContext.getRequestId();
        log.info("Request [{}]: {}", currentRequestId, message);
    }

    /**
     * For demonstration purposes, returns the current request's ID.
     */
    public String getCurrentRequestId() {
        return requestContext.getRequestId();
    }

}