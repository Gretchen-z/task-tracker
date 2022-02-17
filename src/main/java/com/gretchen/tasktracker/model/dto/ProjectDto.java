package com.gretchen.tasktracker.model.dto;

import com.gretchen.tasktracker.model.entity.Task;
import com.gretchen.tasktracker.model.enumeration.ProjectStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Value
@Builder
@Jacksonized
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Schema(name = "Project", description = "Project")
public class ProjectDto {

    @Schema(description = "Project id",
            required = true,
            pattern = "*.",
            maxLength = 36,
            minLength = 36)
    UUID id;

    @Schema(description = "Project name",
            required = true)
    String name;

    @Schema(description = "Project priority")
    int priority;

    @Schema(description = "Project start date")
    LocalDate startDate;

    @Schema(description = "Project completion date")
    LocalDate completionDate;

    @Schema(description = "Project status")
    ProjectStatus status;
}
