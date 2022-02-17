package com.gretchen.tasktracker.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ProjectNotFoundException extends RuntimeException {
    public ProjectNotFoundException(UUID id) {
        super("Project not found: id=" + id);
    }
}
