package com.gretchen.tasktracker.controller;

import com.gretchen.tasktracker.model.dto.*;
import com.gretchen.tasktracker.model.entity.Task;
import com.gretchen.tasktracker.model.exception.TaskNotFoundException;
import com.gretchen.tasktracker.model.mapper.TaskMapper;
import com.gretchen.tasktracker.service.TaskService;
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
@RequestMapping(path = "/tasks")
@Tag(name = "Task", description = "Task management")
@ApiResponse(responseCode = "500", description = "Internal error")
@ApiResponse(responseCode = "400", description = "Validation failed")
@ApiResponse(responseCode = "404", description = "Task not found")
public class TaskController {
    private static final int DEFAULT_PAGINATION_DATA_LIMIT = 10;
    private static final int DEFAULT_PAGE_NUM = 1;

    private final TaskMapper taskMapper;
    private final TaskService taskService;

    @Operation(description = "Find task by id")
    @ApiResponse(responseCode = "200", description = "Task found")
    @GetMapping("/{id}")
    public TaskDto get(@PathVariable(name = "id") UUID id) {
        return Optional.of(id)
                .map(taskService::getAndInitialize)
                .map(taskMapper::toDto)
                .orElseThrow(() -> new TaskNotFoundException(id));
    }

    @Operation(description = "Find all tasks")
    @ApiResponse(responseCode = "200", description = "Tasks found")
    @GetMapping
    public Page<TaskDto> getAll(@RequestParam(required = false) int limit, @RequestParam(required = false) int page) {
        int datLimit = (limit == 0) ? DEFAULT_PAGINATION_DATA_LIMIT : limit;
        int pageNum = (page == 0) ? DEFAULT_PAGE_NUM : page;

        Pageable pageable = PageRequest.of(pageNum, datLimit);
        Page<Task> tasks = taskService.getAll(pageable);
        return tasks.map(taskMapper::toDto);
    }

    @Operation(description = "Find all tasks by project id")
    @ApiResponse(responseCode = "200", description = "Tasks found")
    @GetMapping("/{projectId}")
    public Page<TaskDto> getAllByProjectId(@PathVariable(name = "projectId") UUID projectId, @RequestParam(required = false) int limit, @RequestParam(required = false) int page) {
        int datLimit = (limit == 0) ? DEFAULT_PAGINATION_DATA_LIMIT : limit;
        int pageNum = (page == 0) ? DEFAULT_PAGE_NUM : page;

        Pageable pageable = PageRequest.of(pageNum, datLimit);
        Page<Task> tasks = taskService.getAllByProjectId(projectId, pageable);
        return tasks.map(taskMapper::toDto);
    }

    @Operation(description = "Create task")
    @ApiResponse(responseCode = "200", description = "Task created")
    @PostMapping
    public TaskDto create(@RequestBody TaskCreateDto createDto) {
        return Optional.ofNullable(createDto)
                .map(taskMapper::fromCreateDto)
                .map(taskService::create)
                .map(taskMapper::toDto)
                .orElseThrow();
    }

    @Operation(description = "Update task by id")
    @ApiResponse(responseCode = "200", description = "Task updated")
    @PatchMapping("/{id}")
    public TaskDto update(@PathVariable(name = "id") UUID id, @RequestBody TaskUpdateDto updateDto) {
        return Optional.ofNullable(updateDto)
                .map(taskMapper::fromUpdateDto)
                .map(toUpdate -> taskService.update(id, toUpdate))
                .map(taskMapper::toDto)
                .orElseThrow();
    }

    @Operation(description = "Remove task by id")
    @ApiResponse(responseCode = "204", description = "Task removed")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable(name = "id") UUID id) {
        taskService.delete(id);
    }
}
