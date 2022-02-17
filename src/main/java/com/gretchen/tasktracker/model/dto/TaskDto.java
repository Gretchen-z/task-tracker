package com.gretchen.tasktracker.model.dto;

import com.gretchen.tasktracker.model.entity.Project;
import com.gretchen.tasktracker.model.enumeration.ProjectStatus;
import com.gretchen.tasktracker.model.enumeration.TaskStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Value
@Builder
@Jacksonized
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Schema(name = "Task", description = "Task")
public class TaskDto {

    @Schema(description = "Task id",
            required = true,
            pattern = "*.",
            maxLength = 36,
            minLength = 36)
    UUID id;

    @Schema(description = "Task name",
            required = true)
    String name;

    @Schema(description = "Task priority")
    int priority;

    @Schema(description = "Task description")
    String description;

    @Schema(description = "Tasks project")
    UUID projectId;

    @Schema(description = "Task status")
    TaskStatus status;
}
