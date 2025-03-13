package com.gradleproject.service;


import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION,
        proxyMode = org.springframework.context.annotation.ScopedProxyMode.TARGET_CLASS)
public class SessionScopedBean {

    private final LocalDateTime sessionStart;
    private final AtomicInteger accessCount;

    public SessionScopedBean() {
        this.sessionStart = LocalDateTime.now();
        this.accessCount = new AtomicInteger(0);
    }

    /**
     * Records an action within the session, incrementing an access counter.
     */
    public String recordAccess(String userAction) {
        int count = accessCount.incrementAndGet();
        return "Session started at " + sessionStart + ". Action #" + count + ": " + userAction;
    }

    public LocalDateTime getSessionStart() {
        return sessionStart;
    }

    public int getAccessCount() {
        return accessCount.get();
    }
}