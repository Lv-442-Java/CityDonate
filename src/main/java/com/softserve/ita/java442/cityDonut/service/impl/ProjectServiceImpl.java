package com.softserve.ita.java442.cityDonut.service.impl;

import com.softserve.ita.java442.cityDonut.constant.ErrorMessage;
import com.softserve.ita.java442.cityDonut.dto.category.CategoryNameDto;
import com.softserve.ita.java442.cityDonut.dto.project.EditedProjectDTO;
import com.softserve.ita.java442.cityDonut.dto.project.MainProjectInfoDto;
import com.softserve.ita.java442.cityDonut.dto.project.NewProjectDTO;
import com.softserve.ita.java442.cityDonut.exception.CategoryNotFoundException;
import com.softserve.ita.java442.cityDonut.exception.NotFoundException;
import com.softserve.ita.java442.cityDonut.exception.ProjectNotFoundException;
import com.softserve.ita.java442.cityDonut.mapper.project.EditedProjectMapper;
import com.softserve.ita.java442.cityDonut.mapper.project.MainProjectInfoMapper;
import com.softserve.ita.java442.cityDonut.mapper.project.NewProjectMapper;
import com.softserve.ita.java442.cityDonut.model.Category;
import com.softserve.ita.java442.cityDonut.model.Project;
import com.softserve.ita.java442.cityDonut.model.User;
import com.softserve.ita.java442.cityDonut.repository.CategoryRepository;
import com.softserve.ita.java442.cityDonut.repository.ProjectRepository;
import com.softserve.ita.java442.cityDonut.repository.ProjectStatusRepository;
import com.softserve.ita.java442.cityDonut.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    ProjectStatusRepository projectStatusRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    MainProjectInfoMapper mainProjectInfoMapper;

    @Autowired
    NewProjectMapper newProjectMapper;

    @Autowired
    EditedProjectMapper editedProjectMapper;

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
    public NewProjectDTO saveProject(NewProjectDTO projectDto, long userId) {
        Project projectModel = createProjectModelFromDtoData(projectDto, userId);
        projectModel.setCategories(getValidCategoriesFromCategoriesDto(projectDto.getCategories()));
        Project resultOfQuery = projectRepository.save(projectModel);
        NewProjectDTO result = newProjectMapper.convertToDto(resultOfQuery);
        projectRepository.flush();
        return result;
    }

    @Override
    public EditedProjectDTO editProject(EditedProjectDTO projectDto, long projectId, long userId) {
        Project oldProject = getProjectValidatedByOwner(projectId, userId);
        setDataToEditedProjectModel(oldProject, projectDto);
        oldProject.setCategories(getValidCategoriesFromCategoriesDto(projectDto.getCategories()));
        Project resultOfQuery = projectRepository.save(oldProject);
        EditedProjectDTO result = editedProjectMapper.convertToDto(resultOfQuery);
        projectRepository.flush();
        return result;
    }

    private List<Category> getValidCategoriesFromCategoriesDto(List<CategoryNameDto> categoryNameDtos) {
        List<Category> categories = new ArrayList<>();
        categoryNameDtos.forEach((categoryNameDto) -> categories.add(getValidCategory(categoryNameDto)));
        return categories;
    }

    private Category getValidCategory(CategoryNameDto categoryNameDto) {
        return categoryRepository.findByCategory(categoryNameDto.getCategory())
                .orElseThrow(() -> new CategoryNotFoundException(
                        ErrorMessage.CATEGORY_NOT_FOUND_BY_NAME + categoryNameDto.getCategory()));
    }

    private Project createProjectModelFromDtoData(NewProjectDTO project, long userId) {
        Project projectModel = newProjectMapper.convertToModel(project);
        projectModel.setCreationDate(LocalDateTime.now());
        projectModel.setProjectStatus(projectStatusRepository.getOne(userId));
        projectModel.setOwner(User.builder().id(userId).build());
        return projectModel;
    }

    private Project getProjectValidatedByOwner(long projectId, long userId) {
        return projectRepository.findByOwnerAndId(User.builder().id(userId).build(), projectId)
                .orElseThrow(() -> new ProjectNotFoundException(ErrorMessage.USER_HAS_NOT_PROJECT_WITH_ID + projectId));
    }

    private void setDataToEditedProjectModel(Project destination, EditedProjectDTO source) {
        destination.setName(source.getName());
        destination.setDescription(source.getDescription());
        destination.setLocation(source.getLocation());
        destination.setLocationLatitude(source.getLocationLatitude());
        destination.setLocationLongitude(source.getLocationLongitude());
    }
}
