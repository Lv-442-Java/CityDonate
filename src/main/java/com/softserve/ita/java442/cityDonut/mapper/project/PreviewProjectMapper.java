package com.softserve.ita.java442.cityDonut.mapper.project;

import com.softserve.ita.java442.cityDonut.dto.project.PreviewProjectDto;
import com.softserve.ita.java442.cityDonut.mapper.category.CategoryMapper;
import com.softserve.ita.java442.cityDonut.mapper.projectStatus.ProjectStatusMapper;
import com.softserve.ita.java442.cityDonut.model.Project;

import java.util.ArrayList;
import java.util.List;

public class PreviewProjectMapper {

    public static List<PreviewProjectDto> convertListToDto(List<Project> projects) {
        List<PreviewProjectDto> previewProjectDtos = new ArrayList<>();
        for (Project project : projects) {
            previewProjectDtos.add(convertToDto(project));
        }
        return previewProjectDtos;
    }

    public static PreviewProjectDto convertToDto(Project project) {
        return PreviewProjectDto.builder()
                .id(project.getId())
                .name(project.getName())
                .categories(CategoryMapper.convertListToDto(project.getCategories()))
                .projectStatusDto(ProjectStatusMapper.convertToDto(project.getProjectStatus()))
                .moneyNeeded(project.getMoneyNeeded())
                .build();
    }

    public static Project convertToModel(PreviewProjectDto previewProjectDto) {
        return Project.builder()
                .id(previewProjectDto.getId())
                .name(previewProjectDto.getName())
                .categories(CategoryMapper.convertListToModel(previewProjectDto.getCategories()))
                .projectStatus(ProjectStatusMapper.convertToModel(previewProjectDto.getProjectStatusDto()))
                .moneyNeeded(previewProjectDto.getMoneyNeeded())
                .build();
    }
}
