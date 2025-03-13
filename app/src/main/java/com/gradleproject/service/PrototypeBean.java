package com.gradleproject.service;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import java.util.UUID;

@Component
@Scope("prototype")
public class PrototypeBean {

    private final String taskId;

    public PrototypeBean() {
        // Each new instance gets a unique identifier
        this.taskId = UUID.randomUUID().toString();
    }

    /**
     * Executes a task by reversing the provided input and returning detailed task info.
     */
    public String executeTask(String input) {
        if (input == null) {
            input = "";
        }
        String processed = new StringBuilder(input).reverse().toString();
        return "Task [" + taskId + "] processed input to: " + processed;
    }

    public String getTaskId() {
        return taskId;
    }

}