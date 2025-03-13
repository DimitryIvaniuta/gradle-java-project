package com.gradleproject.service;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

@Service
public class LoggingServiceNoProxy {

    // Inject an ObjectProvider for RequestScopedBeanNoProxy
    private final ObjectProvider<RequestScopedBeanNoProxy> requestScopedBeanProvider;

    public LoggingServiceNoProxy(ObjectProvider<RequestScopedBeanNoProxy> requestScopedBeanProvider) {
        this.requestScopedBeanProvider = requestScopedBeanProvider;
    }

    /**
     * Logs a message including the current request's unique ID.
     * Uses ObjectProvider to fetch the proper instance of RequestScopedBeanNoProxy.
     */
    public String log() {
        RequestScopedBeanNoProxy requestBean = requestScopedBeanProvider.getObject();
        return "Current Request ID: " + requestBean.getRequestId();
    }

}
