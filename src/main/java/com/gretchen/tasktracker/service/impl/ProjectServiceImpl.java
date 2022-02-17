package com.gretchen.tasktracker.service.impl;

import com.gretchen.tasktracker.model.entity.Project;
import com.gretchen.tasktracker.model.entity.Task;
import com.gretchen.tasktracker.model.mapper.ProjectMapper;
import com.gretchen.tasktracker.repository.ProjectRepository;
import com.gretchen.tasktracker.service.ProjectService;
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
public class ProjectServiceImpl implements ProjectService {
    private ProjectRepository projectRepository;
    private ProjectMapper projectMapper;
    private TaskService taskService;

    @Override
    public Project getAndInitialize(UUID id) {
        Project result = projectRepository.findById(id).orElseThrow();
        Hibernate.initialize(result);
        Hibernate.initialize(result.getTasks());
        return result;
    }

    @Override
    public Page<Project> getAll(Pageable pageable) {
        return projectRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public Project create(Project projectJson) {
        return projectRepository.save(projectJson);
    }

    @Override
    @Transactional
    public Project update(UUID id, Project projectJson) {
        return Optional.of(id)
                .map(this::getAndInitialize)
                .map(current -> projectMapper.merge(current, projectJson))
                .map(projectRepository::save)
                .orElseThrow();
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        final Project task = projectRepository.findById(id).orElseThrow();
        projectRepository.delete(task);
    }

    @Override
    @Transactional
    public void assignTask(UUID id, UUID taskId) {
        Project project = getAndInitialize(id);
        Task task = taskService.getAndInitialize(taskId);
        project.addTask(task);
        update(id, project);
    }

    @Override
    @Transactional
    public void deleteTask(UUID id, UUID taskId) {
        Project project = getAndInitialize(id);
        Task task = taskService.getAndInitialize(taskId);
        project.removeTask(task);
        update(id, project);
    }
}
