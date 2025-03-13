package com.gradleproject.service;

import org.springframework.stereotype.Service;

@Service
public class DashboardService {

    // Even though this is injected at startup, ReportGenerationService won't be initialized until needed.
    private final ReportLazyGenerationService reportGenerationService;

    public DashboardService(ReportLazyGenerationService reportGenerationService) {
        this.reportGenerationService = reportGenerationService;
    }

    /**
     * Provides a summary for the dashboard.
     *
     * @return A simple dashboard summary.
     */
    public String getDashboardSummary() {
        return "Dashboard summary: All systems operational.";
    }

    /**
     * Triggers report generation.
     *
     * @return The generated report string.
     */
    public String generateDashboardReport() {
        // When this method is first called, the ReportGenerationService is initialized.
        return reportGenerationService.generateReport();
    }

}
