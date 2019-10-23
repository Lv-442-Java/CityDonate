package com.softserve.ita.java442.cityDonut.mapper;

import com.softserve.ita.java442.cityDonut.dto.ProjectStatusDto;
import com.softserve.ita.java442.cityDonut.model.ProjectStatus;
import org.modelmapper.ModelMapper;

public class ProjectStatusMapper {

    public static ProjectStatusDto convertToDto(ProjectStatus projectStatus) {
        return new ModelMapper().map(projectStatus, ProjectStatusDto.class);
    }

    public static ProjectStatus convertToModel(ProjectStatusDto projectStatusDto) {
        return new ModelMapper().map(projectStatusDto, ProjectStatus.class);
    }
}
