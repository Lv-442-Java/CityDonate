package com.softserve.ita.java442.cityDonut.mapper.projectStatus;

import com.softserve.ita.java442.cityDonut.dto.projectStatus.ProjectStatusDto;
import com.softserve.ita.java442.cityDonut.model.ProjectStatus;

public class ProjectStatusMapper {

    public static ProjectStatusDto convertToDto(ProjectStatus projectStatus) {
        return ProjectStatusDto.builder()
                .id(projectStatus.getId())
                .status(projectStatus.getStatus())
                .build();
    }

    public static ProjectStatus convertToModel(ProjectStatusDto projectStatusDto) {
        return ProjectStatus.builder()
                .id(projectStatusDto.getId())
                .status(projectStatusDto.getStatus())
                .build();
    }
}
