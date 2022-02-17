package com.gretchen.tasktracker.service;

import com.gretchen.tasktracker.model.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface TaskService {
    Task getAndInitialize(UUID id);

    Page<Task> getAll(Pageable pageable);

    Page<Task> getAllByProjectId(UUID projectId, Pageable pageable);

    Task create(Task taskJson);

    Task update(UUID id, Task taskJson);

    void delete(UUID id);
}
