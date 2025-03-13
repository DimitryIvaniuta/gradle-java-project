package com.gradleproject.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Lazy
@Slf4j
public class ReportLazyGenerationService {

    public ReportLazyGenerationService() {
        // Simulate heavy initialization (e.g., loading remote configurations, connecting to external systems)
        log.info("Initializing ReportGenerationService: Loading heavy resources...");
        try {
            Thread.sleep(3000); // Simulate delay for heavy initialization
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Generates a report.
     *
     * @return A string representing the generated report.
     */
    public String generateReport() {
        return "Report generated at " + LocalDateTime.now();
    }

}
