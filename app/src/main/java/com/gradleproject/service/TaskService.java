package com.gradleproject.service;

import com.gradleproject.model.Task;
import com.gradleproject.repository.TaskRepository;
import io.github.resilience4j.retry.annotation.Retry;
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
     * Tries up to 5 times (200ms apart) if the row is locked.
     * On exhaustion, falls back to updateDescriptionFallback().
     */
    @Retry(
            name = "taskService",
            fallbackMethod = "updateDescriptionFallback"
    )
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

    /**
     * Fallback after all retries have failed.
     * Signatures: same args + Throwable.
     */
    public Task updateDescriptionFallback(String name,
                                          String newDesc,
                                          PessimisticLockingFailureException ex) {
        throw new IllegalStateException(
                "Could not update task '" + name +
                        "' because it was constantly locked. Please try again later.", ex);
    }
}
