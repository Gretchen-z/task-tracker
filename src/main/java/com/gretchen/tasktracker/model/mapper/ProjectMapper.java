package com.gretchen.tasktracker.model.mapper;

import com.gretchen.tasktracker.model.dto.*;
import com.gretchen.tasktracker.model.entity.Project;
import org.mapstruct.*;

@Mapper
public interface ProjectMapper {
    @Mapping(target = "tasks", ignore = true)
    Project fromDto(ProjectDto source);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tasks", ignore = true)
    Project fromCreateDto(ProjectCreateDto source);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tasks", ignore = true)
    Project fromUpdateDto(ProjectUpdateDto source);

    ProjectDto toDto(Project source);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Project merge(@MappingTarget Project target, Project source);
}
