package com.softserve.ita.java442.cityDonut.mapper.project;

import com.softserve.ita.java442.cityDonut.dto.project.PreviewProjectDto;
import com.softserve.ita.java442.cityDonut.mapper.GeneralMapper;
import com.softserve.ita.java442.cityDonut.mapper.category.CategoryMapper;
import com.softserve.ita.java442.cityDonut.mapper.projectStatus.ProjectStatusMapper;
import com.softserve.ita.java442.cityDonut.model.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PreviewProjectMapper implements GeneralMapper<Project, PreviewProjectDto> {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private ProjectStatusMapper projectStatusMapper;

    public List<PreviewProjectDto> convertListToDto(List<Project> projects) {
        List<PreviewProjectDto> previewProjectDtos = new ArrayList<>();
        for (Project project : projects) {
            previewProjectDtos.add(convertToDto(project));
        }
        return previewProjectDtos;
    }

    public PreviewProjectDto convertToDto(Project project) {
        return PreviewProjectDto.builder()
                .id(project.getId())
                .name(project.getName())
                .categories(categoryMapper.convertListToDto(project.getCategories()))
                .projectStatusDto(projectStatusMapper.convertToDto(project.getProjectStatus()))
                .moneyNeeded(project.getMoneyNeeded())
                .build();
    }

    public Project convertToModel(PreviewProjectDto previewProjectDto) {
        return Project.builder()
                .id(previewProjectDto.getId())
                .name(previewProjectDto.getName())
                .categories(categoryMapper.convertListToModel(previewProjectDto.getCategories()))
                .projectStatus(projectStatusMapper.convertToModel(previewProjectDto.getProjectStatusDto()))
                .moneyNeeded(previewProjectDto.getMoneyNeeded())
                .build();
    }
}
