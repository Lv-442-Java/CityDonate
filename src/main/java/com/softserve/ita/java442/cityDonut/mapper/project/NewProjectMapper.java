package com.softserve.ita.java442.cityDonut.mapper.project;

import com.softserve.ita.java442.cityDonut.dto.project.NewProjectDTO;
import com.softserve.ita.java442.cityDonut.mapper.status.ProjectStatusMapper;
import com.softserve.ita.java442.cityDonut.mapper.user.UserMapper;
import com.softserve.ita.java442.cityDonut.model.Project;

public class NewProjectMapper {

    public static NewProjectDTO convertToDTO(Project project) {
        return NewProjectDTO.builder()
                .id(project.getId())
                .name(project.getName())
                .description(project.getDescription())
                .location(project.getLocation())
                .locationLatitude(project.getLocationLatitude())
                .locationLongitude(project.getLocationLongitude())
                .creationDate(project.getCreationDate())
                .projectStatusDTO(ProjectStatusMapper.convertToDto(project.getProjectStatus()))
                .ownerDTO(UserMapper.convertToDTO(project.getOwner()))
                .build();
    }

    public static Project convertToModel(NewProjectDTO projectDTO) {
        return Project.builder()
                .id(projectDTO.getId())
                .name(projectDTO.getName())
                .description(projectDTO.getDescription())
                .location(projectDTO.getLocation())
                .locationLatitude(projectDTO.getLocationLatitude())
                .locationLongitude(projectDTO.getLocationLongitude())
                .creationDate(projectDTO.getCreationDate())
                .projectStatus(ProjectStatusMapper.convertToModel(projectDTO.getProjectStatusDTO()))
                .owner(UserMapper.convertToModel(projectDTO.getOwnerDTO()))
                .build();
    }

}
