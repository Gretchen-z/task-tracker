package com.gretchen.tasktracker.model.mapper;

import com.gretchen.tasktracker.model.dto.TaskCreateDto;
import com.gretchen.tasktracker.model.dto.TaskDto;
import com.gretchen.tasktracker.model.dto.TaskUpdateDto;
import com.gretchen.tasktracker.model.entity.Task;
import org.mapstruct.*;

@Mapper
public interface TaskMapper {
    @Mapping(target = "project", ignore = true)
    Task fromDto(TaskDto source);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "project", ignore = true)
    Task fromCreateDto(TaskCreateDto source);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "project", ignore = true)
    Task fromUpdateDto(TaskUpdateDto source);

    @Mapping(target = "projectId", source = "project.id")
    TaskDto toDto(Task source);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Task merge(@MappingTarget Task target, Task source);
}
