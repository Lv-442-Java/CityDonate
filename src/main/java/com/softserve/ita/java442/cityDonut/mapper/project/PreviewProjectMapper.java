package com.softserve.ita.java442.cityDonut.mapper.project;

import com.softserve.ita.java442.cityDonut.dto.project.PreviewProjectDto;
import com.softserve.ita.java442.cityDonut.mapper.GeneralMapper;
import com.softserve.ita.java442.cityDonut.mapper.category.CategoryMapper;
import com.softserve.ita.java442.cityDonut.mapper.projectStatus.ProjectStatusMapper;
import com.softserve.ita.java442.cityDonut.model.Project;

import java.util.ArrayList;
import java.util.List;

public class PreviewProjectMapper implements GeneralMapper<Project,PreviewProjectDto> {

    public List<PreviewProjectDto> convertListToDto(List<Project> projects) {
        List<PreviewProjectDto> previewProjectDtos = new ArrayList<>();
        for (Project project : projects) {
            previewProjectDtos.add(convertToDto(project));
        }
        return previewProjectDtos;
    }

    public PreviewProjectDto convertToDto(Project project) {
        CategoryMapper categoryMapper = new CategoryMapper();
        return PreviewProjectDto.builder()
                .id(project.getId())
                .name(project.getName())
                .categories(categoryMapper.convertListToDto(project.getCategories()))
                .projectStatusDto(ProjectStatusMapper.convertToDto(project.getProjectStatus()))
                .moneyNeeded(project.getMoneyNeeded())
                .build();
    }

    public Project convertToModel(PreviewProjectDto previewProjectDto) {
        CategoryMapper categoryMapper = new CategoryMapper();
        return Project.builder()
                .id(previewProjectDto.getId())
                .name(previewProjectDto.getName())
                .categories(categoryMapper.convertListToModel(previewProjectDto.getCategories()))
                .projectStatus(ProjectStatusMapper.convertToModel(previewProjectDto.getProjectStatusDto()))
                .moneyNeeded(previewProjectDto.getMoneyNeeded())
                .build();
    }
}
