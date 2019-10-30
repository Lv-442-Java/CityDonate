package com.softserve.ita.java442.cityDonut.mapper.status;

import com.softserve.ita.java442.cityDonut.dto.status.ProjectStatusDto;
import com.softserve.ita.java442.cityDonut.mapper.GeneralMapper;
import com.softserve.ita.java442.cityDonut.model.ProjectStatus;

public class ProjectStatusMapper implements GeneralMapper<ProjectStatus, ProjectStatusDto> {

    @Override
    public ProjectStatusDto convertToDto(ProjectStatus projectStatus) {
        return ProjectStatusDto.builder()
                .id(projectStatus.getId())
                .status(projectStatus.getStatus())
                .build();
    }

    @Override
    public ProjectStatus convertToModel(ProjectStatusDto projectStatusDto) {
        return ProjectStatus.builder()
                .id(projectStatusDto.getId())
                .status(projectStatusDto.getStatus())
                .build();
    }
}
