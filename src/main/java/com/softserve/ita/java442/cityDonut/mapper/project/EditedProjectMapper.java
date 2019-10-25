package com.softserve.ita.java442.cityDonut.mapper.project;

import com.softserve.ita.java442.cityDonut.dto.project.EditedProjectDTO;
import com.softserve.ita.java442.cityDonut.dto.project.NewProjectDTO;
import com.softserve.ita.java442.cityDonut.mapper.status.ProjectStatusMapper;
import com.softserve.ita.java442.cityDonut.mapper.user.UserMapper;
import com.softserve.ita.java442.cityDonut.model.Project;

public class EditedProjectMapper {

    public static EditedProjectDTO convertToDTO(Project project) {
        return EditedProjectDTO.builder()
                .id(project.getId())
                .name(project.getName())
                .description(project.getDescription())
                .location(project.getLocation())
                .locationLatitude(project.getLocationLatitude())
                .locationLongitude(project.getLocationLongitude())
                .build();
    }

    public static Project convertToModel(EditedProjectDTO projectDTO) {
        return Project.builder()
                .id(projectDTO.getId())
                .name(projectDTO.getName())
                .description(projectDTO.getDescription())
                .location(projectDTO.getLocation())
                .locationLatitude(projectDTO.getLocationLatitude())
                .locationLongitude(projectDTO.getLocationLongitude())
                .build();
    }

}
