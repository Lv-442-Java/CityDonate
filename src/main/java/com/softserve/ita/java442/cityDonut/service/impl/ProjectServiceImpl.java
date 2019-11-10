package com.softserve.ita.java442.cityDonut.service.impl;

import com.softserve.ita.java442.cityDonut.constant.ErrorMessage;
import com.softserve.ita.java442.cityDonut.dto.category.CategoryDto;
import com.softserve.ita.java442.cityDonut.dto.category.CategoryNameDto;
import com.softserve.ita.java442.cityDonut.dto.project.*;
import com.softserve.ita.java442.cityDonut.exception.CategoryNotFoundException;
import com.softserve.ita.java442.cityDonut.exception.NotEnoughPermission;
import com.softserve.ita.java442.cityDonut.exception.NotFoundException;
import com.softserve.ita.java442.cityDonut.exception.ProjectNotFoundException;
import com.softserve.ita.java442.cityDonut.mapper.category.CategoryMapper;
import com.softserve.ita.java442.cityDonut.mapper.project.*;
import com.softserve.ita.java442.cityDonut.model.*;
import com.softserve.ita.java442.cityDonut.repository.*;
import com.softserve.ita.java442.cityDonut.service.CategoryService;
import com.softserve.ita.java442.cityDonut.service.ProjectService;
import com.softserve.ita.java442.cityDonut.service.ProjectStatusService;
import com.softserve.ita.java442.cityDonut.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectStatusService projectStatusService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private PreviewProjectMapper previewProjectMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private MainProjectInfoMapper mainProjectInfoMapper;

    @Autowired
    private DonatedUserProjectRepository donatedUserProjectRepository;

    @Autowired
    private ProjectStatusRepository projectStatusRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProjectByUserDonateMapper projectByUserDonateMapper;

    @Autowired
    private NewProjectMapper newProjectMapper;

    @Autowired
    private EditedProjectMapper editedProjectMapper;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Override
    public MainProjectInfoDto getProjectById(long id) {
        MainProjectInfoDto mainProjectInfoDto;
        try {
            mainProjectInfoDto = mainProjectInfoMapper.convertToDto(projectRepository.getById(id));
        } catch (NullPointerException e) {
            throw new NotFoundException(ErrorMessage.PROJECT_NOT_FOUND_BY_ID);
        }
        return mainProjectInfoDto;
    }

    @Override
    public List<PreviewProjectDto> getFilteredProjects
            (List<String> categoryIds, String statusId, long moneyFrom, String moneyTo, Pageable pageable) {
        if (moneyTo.equals("default")) {
            moneyTo = projectRepository.getMaxByMoneyNeeded().toString();
        }
        long realMoneyTo = Long.parseLong(moneyTo);
        if (categoryIds.get(0).equals("default")) {
            if (statusId.equals("default")) {
                return previewProjectMapper.convertListToDto(
                        projectRepository.findAllByMoneyNeededBetween(moneyFrom, realMoneyTo, pageable));
            }
            return previewProjectMapper.convertListToDto(
                    projectRepository.findAllByProjectStatusIdAndMoneyNeededBetween(
                            Long.parseLong(statusId), moneyFrom, realMoneyTo, pageable));
        } else if (statusId.equals("default")) {
            return previewProjectMapper.convertListToDto(
                    projectRepository.getFilteredProjectsByCategories(
                            categoryService.getCategoriesByIds(categoryIds),
                            moneyFrom, realMoneyTo, categoryIds.size(), pageable));
        } else {
            return previewProjectMapper.convertListToDto(
                    projectRepository.getFilteredProjects(categoryService.getCategoriesByIds(categoryIds),
                            projectStatusService.getById(Long.parseLong(statusId)),
                            moneyFrom, realMoneyTo, categoryIds.size(), pageable));
        }
    }

    @Override
    public List<ProjectByUserDonateDto> getDonatedUserProject(long id, Pageable pageable) {
        List<DonatedUserProject> donatedUserProjects = donatedUserProjectRepository.findDonatedUserProject(id, pageable);
        List<ProjectByUserDonateDto> projectByUserDonateDtos = new LinkedList<>();
        for (DonatedUserProject donatedUserProject : donatedUserProjects) {
            projectByUserDonateDtos.add(projectByUserDonateMapper.convertToDto(donatedUserProject));
        }
        return projectByUserDonateDtos;
    }

    private void filterByCategories(List<PreviewProjectDto> previewProjectDtos, List<CategoryDto> categoryDtos) {
        previewProjectDtos.removeIf(previewProjectDto -> !previewProjectDto.getCategories().containsAll(categoryDtos));
    }

    @Override
    @Transactional
    public NewProjectDto saveProject(NewProjectDto projectDto, long userId) {
        Project projectModel = createProjectModelFromDtoData(projectDto, userId);
        projectModel.setCategories(getValidCategoriesFromCategoriesDto(projectDto.getCategories()));
        Project resultOfQuery = projectRepository.save(projectModel);
        NewProjectDto result = newProjectMapper.convertToDto(resultOfQuery);
        projectRepository.flush();
        return result;
    }

    @Override
    @Transactional
    public EditedProjectDto editProject(EditedProjectDto projectDto, long projectId, long userId) {
        Project oldProject = getProjectValidatedByOwner(projectId, userId);
        setDataToEditedProjectModel(oldProject, projectDto);
        oldProject.setCategories(getValidCategoriesFromCategoriesDto(projectDto.getCategories()));
        Project resultOfQuery = projectRepository.save(oldProject);
        EditedProjectDto result = editedProjectMapper.convertToDto(resultOfQuery);
        projectRepository.flush();
        return result;
    }

    @Override
    public Long getMaxMoneyNeeded() {
        return projectRepository.getMaxByMoneyNeeded();
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
        destination.setMoneyNeeded(source.getMoneyNeeded());
    }

    @Override
    public List<ProjectInfoDto> getFreeProject() {
        String neededStatus = "очікує підтвердження";
        ProjectStatus projectStatus = projectStatusRepository.getProjectStatusByStatus(neededStatus);
        if (projectStatus == null) {
            throw new NotFoundException(ErrorMessage.PROJECT_STATUS_NOT_FOUND + neededStatus);
        }
        List<Project> projects = projectRepository.findProjectsByProjectStatus(projectStatus);
        List<ProjectInfoDto> list = new ArrayList<>();
        for (Project project : projects) {
            ProjectInfoDto projectInfoDto = ProjectInfoDto.builder()
                    .id(project.getId())
                    .name(project.getName())
                    .creationDate(project.getCreationDate())
                    .ownerFirstName(project.getOwner().getFirstName())
                    .ownerLastName(project.getOwner().getLastName())
                    .build();
            list.add(projectInfoDto);
        }
        return list;
    }

    @Override
    @Transactional
    public MainProjectInfoDto addModeratorToProject(long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.PROJECT_NOT_FOUND_BY_ID + projectId));
        User user = userService.getCurrentUser();
        String userRole = "user";
        Role role = roleRepository.findByRole(userRole);
        if (role == null) {
            throw new NotFoundException(ErrorMessage.ROLE_NOT_FOUND + userRole);
        }
        if (user.getRole().equals(roleRepository.findByRole(userRole))) {
            throw new NotEnoughPermission(ErrorMessage.NOT_ENOUGH_PERMISSION);
        }
        List<User> moderatorList = project.getModerators();
        if (moderatorList.size() == 0) {
            ProjectStatus projectStatus = projectStatusRepository.getProjectStatusByStatus("на перевірці");
            if (projectStatus == null) {
                throw new NotFoundException(ErrorMessage.PROJECT_STATUS_NOT_FOUND);
            }
            project.setProjectStatus(projectStatus);
        }
        moderatorList.add(user);
        project.setModerators(moderatorList);
        return mainProjectInfoMapper.convertToDto(projectRepository.save(project));
    }
}
