package com.softserve.ita.java442.cityDonut.mapper;

import com.softserve.ita.java442.cityDonut.dto.ProjectDto;
import com.softserve.ita.java442.cityDonut.model.Project;
import org.modelmapper.ModelMapper;

public class ProjectMapper {

    public static ProjectDto convertToDto(Project project) {
        return new ModelMapper().map(project, ProjectDto.class);
    }

    public static Project convertToModel(ProjectDto projectDto) {
        return new ModelMapper().map(projectDto, Project.class);
    }
}
