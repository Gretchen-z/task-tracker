package com.gretchen.tasktracker.controller;

import com.gretchen.tasktracker.model.dto.*;
import com.gretchen.tasktracker.model.entity.Project;
import com.gretchen.tasktracker.model.exception.ProjectNotFoundException;
import com.gretchen.tasktracker.model.mapper.ProjectMapper;
import com.gretchen.tasktracker.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/projects")
@Tag(name = "Project", description = "Project management")
@ApiResponse(responseCode = "500", description = "Internal error")
@ApiResponse(responseCode = "400", description = "Validation failed")
@ApiResponse(responseCode = "404", description = "Project not found")
public class ProjectController {
    private static final int DEFAULT_PAGINATION_DATA_LIMIT = 10;
    private static final int DEFAULT_PAGE_NUM = 1;

    private final ProjectMapper projectMapper;
    private final ProjectService projectService;

    @Operation(description = "Find project by id")
    @ApiResponse(responseCode = "200", description = "Project found")
    @GetMapping("/{id}")
    public ProjectDto get(@PathVariable(name = "id") UUID id) {
        return Optional.of(id)
                .map(projectService::getAndInitialize)
                .map(projectMapper::toDto)
                .orElseThrow(() -> new ProjectNotFoundException(id));
    }

    @Operation(description = "Find all projects")
    @ApiResponse(responseCode = "200", description = "Projects found")
    @GetMapping
    public Page<ProjectDto> getAll(@RequestParam(required = false) int limit, @RequestParam(required = false) int page) {
        int datLimit = (limit == 0) ? DEFAULT_PAGINATION_DATA_LIMIT : limit;
        int pageNum = (page == 0) ? DEFAULT_PAGE_NUM : page;

        Pageable pageable = PageRequest.of(pageNum, datLimit);
        Page<Project> projects = projectService.getAll(pageable);
        return projects.map(projectMapper::toDto);
    }

    @Operation(description = "Create project")
    @ApiResponse(responseCode = "200", description = "Project created")
    @PostMapping
    public ProjectDto create(@RequestBody ProjectCreateDto createDto) {
        return Optional.ofNullable(createDto)
                .map(projectMapper::fromCreateDto)
                .map(projectService::create)
                .map(projectMapper::toDto)
                .orElseThrow();
    }

    @Operation(description = "Update project by id")
    @ApiResponse(responseCode = "200", description = "Project updated")
    @PatchMapping("/{id}")
    public ProjectDto update(@PathVariable(name = "id") UUID id, @RequestBody ProjectUpdateDto updateDto) {
        return Optional.ofNullable(updateDto)
                .map(projectMapper::fromUpdateDto)
                .map(toUpdate -> projectService.update(id, toUpdate))
                .map(projectMapper::toDto)
                .orElseThrow();
    }

    @Operation(description = "Remove project by id")
    @ApiResponse(responseCode = "204", description = "Project removed")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable(name = "id") UUID id) {
        projectService.delete(id);
    }

    @Operation(description = "Add task by id")
    @ApiResponse(responseCode = "200", description = "Task added")
    @PatchMapping("/{id}/tasks/{taskId}")
    public void assignTask(@PathVariable UUID id, @PathVariable UUID taskId) {
        projectService.assignTask(id, taskId);
    }

    @Operation(description = "Remove task by id")
    @ApiResponse(responseCode = "204", description = "Task removed")
    @DeleteMapping("/{id}/tasks/{taskId}")
    public void deleteTask(@PathVariable UUID id, @PathVariable UUID taskId) {
        projectService.deleteTask(id, taskId);
    }
}
