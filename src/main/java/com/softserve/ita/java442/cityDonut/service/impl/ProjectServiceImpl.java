package com.softserve.ita.java442.cityDonut.service.impl;

import com.softserve.ita.java442.cityDonut.constant.ErrorMessage;
import com.softserve.ita.java442.cityDonut.dto.category.CategoryDto;
import com.softserve.ita.java442.cityDonut.dto.project.MainProjectInfoDto;
import com.softserve.ita.java442.cityDonut.dto.project.PreviewProjectDto;
import com.softserve.ita.java442.cityDonut.exception.NotFoundException;
import com.softserve.ita.java442.cityDonut.mapper.category.CategoryMapper;
import com.softserve.ita.java442.cityDonut.mapper.project.MainProjectInfoMapper;
import com.softserve.ita.java442.cityDonut.mapper.project.PreviewProjectMapper;
import com.softserve.ita.java442.cityDonut.repository.ProjectRepository;
import com.softserve.ita.java442.cityDonut.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectStatusServiceImpl projectStatusService;

    @Autowired
    private CategoryServiceImpl categoryService;

    @Autowired
    private PreviewProjectMapper previewProjectMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private MainProjectInfoMapper mainProjectInfoMapper;

    @Override
    public MainProjectInfoDto getProjectById(long id) {
        MainProjectInfoDto mainProjectInfoDto;
        try {
            mainProjectInfoDto = mainProjectInfoMapper.convertToDto(projectRepository.getById(id));
        } catch (NullPointerException e){
            throw new NotFoundException(ErrorMessage.PROJECT_NOT_FOUND_BY_ID);
        }
        return mainProjectInfoDto;
    }

    @Override
    public List<PreviewProjectDto> getFilteredProjects
            (List<String> categories, long moneyFrom, long moneyTo, String status) {
        List<PreviewProjectDto> filteredProjects = previewProjectMapper.convertListToDto(
                projectRepository.getProjectsByProjectStatusAndMoneyNeededBetween(
                        projectStatusService.getByStatus(status), moneyFrom, moneyTo));
        filterByCategories(filteredProjects,
                categoryMapper.convertListToDto(categoryService.getCategoriesByCategories(categories)));
        return filteredProjects;
    }

    private void filterByCategories(List<PreviewProjectDto> previewProjectDtos, List<CategoryDto> categoryDtos) {
        previewProjectDtos.removeIf(previewProjectDto -> !previewProjectDto.getCategories().containsAll(categoryDtos));
    }
}
