package com.softserve.ita.java442.cityDonut.mapper;

import com.softserve.ita.java442.cityDonut.dto.ProjectStatusDto;
import com.softserve.ita.java442.cityDonut.dto.status.ProjectStatusDTO;
import com.softserve.ita.java442.cityDonut.model.ProjectStatus;
import org.modelmapper.ModelMapper;

public class ProjectStatusMapper {

    public static ProjectStatusDTO convertToDto(ProjectStatus projectStatus) {
        return ProjectStatusDTO.builder()
                .id(projectStatus.getId())
                .status(projectStatus.getStatus())
                .build();
    }

    public static ProjectStatus convertToModel(ProjectStatusDTO projectStatusDto) {
        return ProjectStatus.builder()
                .id(projectStatusDto.getId())
                .status(projectStatusDto.getStatus())
                .build();
    }
}
