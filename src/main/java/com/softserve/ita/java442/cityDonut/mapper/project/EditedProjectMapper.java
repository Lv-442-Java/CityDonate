package com.softserve.ita.java442.cityDonut.mapper.project;

import com.softserve.ita.java442.cityDonut.dto.project.EditedProjectDto;
import com.softserve.ita.java442.cityDonut.mapper.GeneralMapper;
import com.softserve.ita.java442.cityDonut.mapper.category.CategoryNameMapper;
import com.softserve.ita.java442.cityDonut.model.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EditedProjectMapper implements GeneralMapper<Project, EditedProjectDto> {

    @Autowired
    private CategoryNameMapper categoryNameMapper;

    @Override
    public EditedProjectDto convertToDto(Project project) {
        return EditedProjectDto.builder()
                .id(project.getId())
                .name(project.getName())
                .description(project.getDescription())
                .location(project.getLocation())
                .locationLatitude(project.getLocationLatitude())
                .locationLongitude(project.getLocationLongitude())
                .categories(categoryNameMapper.convertListToDto(project.getCategories()))
                .build();
    }

    @Override
    public Project convertToModel(EditedProjectDto projectDTO) {
        return Project.builder()
                .id(projectDTO.getId())
                .name(projectDTO.getName())
                .description(projectDTO.getDescription())
                .location(projectDTO.getLocation())
                .locationLatitude(projectDTO.getLocationLatitude())
                .locationLongitude(projectDTO.getLocationLongitude())
                .categories(categoryNameMapper.convertListToModel(projectDTO.getCategories()))
                .build();
    }

}
