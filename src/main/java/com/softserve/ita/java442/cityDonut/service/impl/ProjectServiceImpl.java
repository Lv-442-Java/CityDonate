package com.softserve.ita.java442.cityDonut.service.impl;

import com.softserve.ita.java442.cityDonut.constant.ErrorMessage;
import com.softserve.ita.java442.cityDonut.dto.category.CategoryNameDto;
import com.softserve.ita.java442.cityDonut.dto.project.EditedProjectDto;
import com.softserve.ita.java442.cityDonut.dto.category.CategoryDto;
import com.softserve.ita.java442.cityDonut.dto.project.MainProjectInfoDto;
import com.softserve.ita.java442.cityDonut.dto.project.NewProjectDto;
import com.softserve.ita.java442.cityDonut.exception.CategoryNotFoundException;
import com.softserve.ita.java442.cityDonut.dto.project.PreviewProjectDto;
import com.softserve.ita.java442.cityDonut.dto.project.ProjectByUserDonateDto;
import com.softserve.ita.java442.cityDonut.exception.NotFoundException;
import com.softserve.ita.java442.cityDonut.exception.ProjectNotFoundException;
import com.softserve.ita.java442.cityDonut.mapper.project.EditedProjectMapper;
import com.softserve.ita.java442.cityDonut.mapper.category.CategoryMapper;
import com.softserve.ita.java442.cityDonut.mapper.project.MainProjectInfoMapper;
import com.softserve.ita.java442.cityDonut.mapper.project.NewProjectMapper;
import com.softserve.ita.java442.cityDonut.model.Category;
import com.softserve.ita.java442.cityDonut.model.Project;
import com.softserve.ita.java442.cityDonut.model.User;
import com.softserve.ita.java442.cityDonut.repository.CategoryRepository;
import com.softserve.ita.java442.cityDonut.mapper.project.PreviewProjectMapper;
import com.softserve.ita.java442.cityDonut.mapper.project.ProjectByUserDonateMapper;
import com.softserve.ita.java442.cityDonut.model.DonatedUserProject;
import com.softserve.ita.java442.cityDonut.model.Project;
import com.softserve.ita.java442.cityDonut.repository.DonatedUserProjectRepository;
import com.softserve.ita.java442.cityDonut.repository.ProjectRepository;
import com.softserve.ita.java442.cityDonut.repository.ProjectStatusRepository;
import com.softserve.ita.java442.cityDonut.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    @Autowired
    private DonatedUserProjectRepository donatedUserProjectRepository;

    @Autowired
    ProjectStatusRepository projectStatusRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    private ProjectByUserDonateMapper projectByUserDonateMapper;

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
    public List<PreviewProjectDto> getFilteredProjects
            (List<String> categories, long moneyFrom, long moneyTo, String status) {
        List<PreviewProjectDto> filteredProjects = previewProjectMapper.convertListToDto(
                projectRepository.getProjectsByProjectStatusAndMoneyNeededBetween(
                        projectStatusService.getByStatus(status), moneyFrom, moneyTo));
        filterByCategories(filteredProjects,
                categoryMapper.convertListToDto(categoryService.getCategoriesByCategories(categories)));
        return filteredProjects;
    }

    @Override
    public List<ProjectByUserDonateDto> getDonatedUserProject(long id) {
        List<DonatedUserProject> donatedUserProjects = donatedUserProjectRepository.findDonatedUserProject(id);
        List<ProjectByUserDonateDto> projectByUserDonateDtos = new LinkedList<>();
        for (DonatedUserProject donatedUserProject: donatedUserProjects) {
            Project project = projectRepository.getById(donatedUserProject.getProjectId());
            projectByUserDonateDtos.add(projectByUserDonateMapper.convertToDto(project,donatedUserProject));
        }
        return projectByUserDonateDtos;
    }

    private void filterByCategories(List<PreviewProjectDto> previewProjectDtos, List<CategoryDto> categoryDtos) {
        previewProjectDtos.removeIf(previewProjectDto -> !previewProjectDto.getCategories().containsAll(categoryDtos));
    }

    @Override
    public NewProjectDto saveProject(NewProjectDto projectDto, long userId) {
        Project projectModel = createProjectModelFromDtoData(projectDto, userId);
        projectModel.setCategories(getValidCategoriesFromCategoriesDto(projectDto.getCategories()));
        Project resultOfQuery = projectRepository.save(projectModel);
        NewProjectDto result = newProjectMapper.convertToDto(resultOfQuery);
        projectRepository.flush();
        return result;
    }

    @Override
    public EditedProjectDto editProject(EditedProjectDto projectDto, long projectId, long userId) {
        Project oldProject = getProjectValidatedByOwner(projectId, userId);
        setDataToEditedProjectModel(oldProject, projectDto);
        oldProject.setCategories(getValidCategoriesFromCategoriesDto(projectDto.getCategories()));
        Project resultOfQuery = projectRepository.save(oldProject);
        EditedProjectDto result = editedProjectMapper.convertToDto(resultOfQuery);
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

    private Project createProjectModelFromDtoData(NewProjectDto project, long userId) {
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

    private void setDataToEditedProjectModel(Project destination, EditedProjectDto source) {
        destination.setName(source.getName());
        destination.setDescription(source.getDescription());
        destination.setLocation(source.getLocation());
        destination.setLocationLatitude(source.getLocationLatitude());
        destination.setLocationLongitude(source.getLocationLongitude());
    }
}
