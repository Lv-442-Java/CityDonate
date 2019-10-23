package com.softserve.ita.java442.cityDonut.service.impl;

import com.softserve.ita.java442.cityDonut.dto.category.CategoryDto;
import com.softserve.ita.java442.cityDonut.dto.project.PreviewProjectDto;
import com.softserve.ita.java442.cityDonut.mapper.category.CategoryMapper;
import com.softserve.ita.java442.cityDonut.mapper.project.PreviewProjectMapper;
import com.softserve.ita.java442.cityDonut.repository.ProjectRepository;
import com.softserve.ita.java442.cityDonut.service.ProjectService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class ProjectServiceImpl implements ProjectService {

    ProjectRepository projectRepository;
    ProjectStatusServiceImpl projectStatusService;
    CategoryServiceImpl categoryService;

    @Override
    public List<PreviewProjectDto> getFilteredProjects
            (List<String> categories, long moneyFrom, long moneyTo, String status) {
        List<PreviewProjectDto> filteredProjects = PreviewProjectMapper.convertListToDto(
                projectRepository.getProjectsByProjectStatusAndMoneyNeededBetween(
                        projectStatusService.getByStatus(status), moneyFrom, moneyTo));
        filterByCategories(filteredProjects,
                CategoryMapper.convertListToDto(categoryService.getCategoriesByCategories(categories)));
        return filteredProjects;
    }

    private void filterByCategories(List<PreviewProjectDto> previewProjectDtos, List<CategoryDto> categoryDtos) {
        previewProjectDtos.removeIf(previewProjectDto -> !previewProjectDto.getCategories().containsAll(categoryDtos));
    }
}
