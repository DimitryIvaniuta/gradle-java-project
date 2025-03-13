package com.gradleproject.controller;

import com.gradleproject.service.ApplicationScopedBean;
import com.gradleproject.service.PrototypeBean;
import com.gradleproject.service.RequestScopedBean;
import com.gradleproject.service.SessionScopedBean;
import com.gradleproject.service.SingletonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
public class ScopeController {

    private final SingletonService singletonService;
    private final PrototypeBean prototypeBean;
    private final RequestScopedBean requestScopedBean;
    private final SessionScopedBean sessionScopedBean;
    private final ApplicationScopedBean applicationScopedBean;

    @Autowired
    public ScopeController(SingletonService singletonService,
                           PrototypeBean prototypeBean,
                           RequestScopedBean requestScopedBean,
                           SessionScopedBean sessionScopedBean,
                           ApplicationScopedBean applicationScopedBean) {
        this.singletonService = singletonService;
        this.prototypeBean = prototypeBean;
        this.requestScopedBean = requestScopedBean;
        this.sessionScopedBean = sessionScopedBean;
        this.applicationScopedBean = applicationScopedBean;
    }

    /**
     * Processes comma-separated items using the SingletonService.
     */
    @GetMapping("/singleton")
    public String singleton(@RequestParam(defaultValue = "apple,banana,orange") String items) {
        String[] data = items.split(",");
        return singletonService.processBusinessLogic(Arrays.asList(data));
    }

    /**
     * Executes a task using the PrototypeBean.
     */
    @GetMapping("/prototype")
    public String prototype(@RequestParam(defaultValue = "HelloWorld") String input) {
        return prototypeBean.executeTask(input);
    }

    /**
     * Generates a request trace using the RequestScopedBean.
     */
    @GetMapping("/request")
    public String requestScope(@RequestParam(defaultValue = "No details provided") String details) {
        return requestScopedBean.getRequestTrace(details);
    }

    /**
     * Records a session action using the SessionScopedBean.
     */
    @GetMapping("/session")
    public String sessionScope(@RequestParam(defaultValue = "default action") String action) {
        return sessionScopedBean.recordAccess(action);
    }

    /**
     * Retrieves or updates global application configuration using the ApplicationScopedBean.
     * To update a configuration, provide both 'key' and 'value' parameters.
     */
    @GetMapping("/application")
    public String applicationScope(@RequestParam(required = false) String key,
                                   @RequestParam(required = false) String value) {
        if (key != null && value != null) {
            return applicationScopedBean.updateConfiguration(key, value);
        }
        return "App Name: " + applicationScopedBean.getConfigValue("app.name") +
                ", App Version: " + applicationScopedBean.getConfigValue("app.version");
    }
}