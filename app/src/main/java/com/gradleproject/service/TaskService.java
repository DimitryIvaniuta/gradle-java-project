package com.gradleproject.service;

import com.gradleproject.model.Task;
import com.gradleproject.repository.TaskRepository;
import org.springframework.dao.PessimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
public class TaskService {

    private final TaskRepository repo;

    public TaskService(TaskRepository repo) {
        this.repo = repo;
    }

    /**
     * Updates a task's description with row-level lock to prevent concurrent write conflicts.
     */
    @Transactional
    public Task updateDescription(String name, String newDesc) {
        try {
            Task task = repo.findByNameForUpdate(name)
                    .orElseThrow(() -> new NoSuchElementException("No task: " + name));
            task.setDescription(newDesc);
            return repo.save(task);
        } catch (PessimisticLockingFailureException ex) {
            throw new IllegalStateException("Task is currently being updated by another user", ex);
        }
    }

}
