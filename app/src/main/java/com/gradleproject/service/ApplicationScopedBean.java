package com.gradleproject.service;


import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

@Component
@Scope("application")
public class ApplicationScopedBean {

    private final Map<String, String> configuration;

    public ApplicationScopedBean() {
        // Simulate loading global configuration from an external source
        this.configuration = new ConcurrentHashMap<>();
        configuration.put("app.name", "SpringBootScopedApp");
        configuration.put("app.version", "1.0.0");
    }

    /**
     * Retrieves a configuration value based on a provided key.
     */
    public String getConfigValue(String key) {
        return configuration.getOrDefault(key, "Not Found");
    }

    /**
     * Returns all configuration entries.
     */
    public Map<String, String> getAllConfigurations() {
        return configuration;
    }

    /**
     * Updates a configuration key with a new value.
     */
    public String updateConfiguration(String key, String value) {
        configuration.put(key, value);
        return "Updated configuration: " + key + " = " + value;
    }

}