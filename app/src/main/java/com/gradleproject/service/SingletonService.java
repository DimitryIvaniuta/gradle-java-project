package com.gradleproject.service;

import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@EqualsAndHashCode
public class SingletonService {

    /**
     * Processes a list of items by concatenating them and converting to uppercase.
     * In a real-world scenario, this could be a business service that applies complex logic.
     */
    public String processBusinessLogic(List<String> data) {
        if (data == null || data.isEmpty()) {
            return "No data provided.";
        }
        String result = String.join(", ", data).toUpperCase();
        return "Processed Data: " + result;
    }

    public String getServiceInfo() {
        return "SingletonService instance hash: " + this.hashCode();
    }

}