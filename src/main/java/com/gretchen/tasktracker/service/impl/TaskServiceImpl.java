package com.gretchen.tasktracker.service.impl;

import com.gretchen.tasktracker.model.entity.Task;
import com.gretchen.tasktracker.model.mapper.TaskMapper;
import com.gretchen.tasktracker.repository.TaskRepository;
import com.gretchen.tasktracker.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TaskServiceImpl implements TaskService {
    private TaskRepository taskRepository;
    private TaskMapper taskMapper;

    @Override
    public Task getAndInitialize(UUID id) {
        Task result = taskRepository.findById(id).orElseThrow();
        Hibernate.initialize(result);
        Hibernate.initialize(result.getProject());
        return result;
    }

    @Override
    public Page<Task> getAll(Pageable pageable) {
        return taskRepository.findAll(pageable);
    }

    @Override
    public Page<Task> getAllByProjectId(UUID projectId, Pageable pageable) {
        return taskRepository.findAllByProjectId(projectId, pageable);
    }

    @Override
    @Transactional
    public Task create(Task taskJson) {
        return taskRepository.save(taskJson);
    }

    @Override
    @Transactional
    public Task update(UUID id, Task taskJson) {
        return Optional.of(id)
                .map(this::getAndInitialize)
                .map(current -> taskMapper.merge(current, taskJson))
                .map(taskRepository::save)
                .orElseThrow();
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        final Task task = taskRepository.findById(id).orElseThrow();
        taskRepository.delete(task);
    }
}
