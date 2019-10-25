package com.softserve.ita.java442.cityDonut.mapper;

import com.softserve.ita.java442.cityDonut.dto.project.ProjectForCommentDto;
import com.softserve.ita.java442.cityDonut.model.Project;
import org.springframework.stereotype.Component;

@Component
public class ProjectForCommentMapper implements GeneralMapper<Project, ProjectForCommentDto> {

    @Override
    public ProjectForCommentDto convertToDto(Project model) {
       return ProjectForCommentDto.builder()
               .id(model.getId()).build();
    }

    @Override
    public Project convertToModel(ProjectForCommentDto dto) {
        return  Project.builder()
                .id(dto.getId()).build();
    }
}
