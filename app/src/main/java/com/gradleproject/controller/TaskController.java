package com.gradleproject.controller;

import com.gradleproject.model.Task;
import com.gradleproject.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService service;
    public TaskController(TaskService service) { this.service = service; }

    @PutMapping("/{name}")
    public ResponseEntity<Task> update(
            @PathVariable String name,
            @RequestBody Map<String,String> body) {
        String desc = body.get("description");
        Task updated = service.updateDescription(name, desc);
        return ResponseEntity.ok(updated);
    }
}