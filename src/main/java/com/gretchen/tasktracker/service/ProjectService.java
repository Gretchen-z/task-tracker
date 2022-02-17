package com.gretchen.tasktracker.service;

import com.gretchen.tasktracker.model.entity.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ProjectService {
    Project getAndInitialize(UUID id);

    Page<Project> getAll(Pageable pageable);

    Project create(Project projectJson);

    Project update(UUID id, Project projectJson);

    void delete(UUID id);

    void assignTask(UUID id, UUID taskId);

    void deleteTask(UUID id, UUID taskId);
}
