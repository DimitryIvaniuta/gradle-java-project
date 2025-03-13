package com.gradleproject.controller;

import com.gradleproject.service.ApplicationScopedBean;
import com.gradleproject.service.DashboardService;
import com.gradleproject.service.LoggingService;
import com.gradleproject.service.LoggingServiceNoProxy;
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

    private final LoggingService loggingService;

    private final LoggingServiceNoProxy loggingServiceNoProxy;

    private final DashboardService dashboardService;

    @Autowired
    public ScopeController(SingletonService singletonService,
                           PrototypeBean prototypeBean,
                           RequestScopedBean requestScopedBean,
                           SessionScopedBean sessionScopedBean,
                           ApplicationScopedBean applicationScopedBean,
                           LoggingService loggingService,
                           LoggingServiceNoProxy loggingServiceNoProxy,
                           DashboardService dashboardService) {
        this.singletonService = singletonService;
        this.prototypeBean = prototypeBean;
        this.requestScopedBean = requestScopedBean;
        this.sessionScopedBean = sessionScopedBean;
        this.applicationScopedBean = applicationScopedBean;
        this.loggingService = loggingService;
        this.loggingServiceNoProxy = loggingServiceNoProxy;
        this.dashboardService = dashboardService;
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

    /**
     * Proxy Usage.
     */
    @GetMapping("/proxy-scope")
    public String demo() {
        // Log a message for the current request.
        loggingService.log("Processing demo request");
        // Return the current request ID to show that it changes per request.
        return "Current Request ID: " + loggingService.getCurrentRequestId();
    }


    @GetMapping("/no-proxy-scope")
    public String noProxyScope() {
        // Each HTTP request will obtain its own RequestScopedBeanNoProxy instance.
        return loggingServiceNoProxy.log();
    }


    /**
     * Endpoint to retrieve the dashboard summary.
     */
    @GetMapping("/dashboard")
    public String getDashboard() {
        return dashboardService.getDashboardSummary();
    }

    /**
     * Endpoint to generate a report.
     * This call will trigger the lazy initialization of ReportGenerationService.
     */
    @GetMapping("/dashboard/report")
    public String getDashboardReport() {
        return dashboardService.generateDashboardReport();
    }

}