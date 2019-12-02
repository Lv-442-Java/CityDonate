package com.softserve.ita.java442.cityDonut.service.impl;

import com.softserve.ita.java442.cityDonut.constant.ErrorMessage;
import com.softserve.ita.java442.cityDonut.dto.category.CategoryNameDto;
import com.softserve.ita.java442.cityDonut.dto.project.*;
import com.softserve.ita.java442.cityDonut.exception.CategoryNotFoundException;
import com.softserve.ita.java442.cityDonut.exception.NotEnoughPermission;
import com.softserve.ita.java442.cityDonut.exception.NotFoundException;
import com.softserve.ita.java442.cityDonut.exception.ProjectNotFoundException;
import com.softserve.ita.java442.cityDonut.mapper.project.*;
import com.softserve.ita.java442.cityDonut.model.*;
import com.softserve.ita.java442.cityDonut.repository.*;
import com.softserve.ita.java442.cityDonut.service.CategoryService;
import com.softserve.ita.java442.cityDonut.service.ProjectService;
import com.softserve.ita.java442.cityDonut.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {


    private ProjectRepository projectRepository;
    private CategoryService categoryService;
    private PreviewProjectMapper previewProjectMapper;
    private MainProjectInfoMapper mainProjectInfoMapper;
    private DonatedUserProjectRepository donatedUserProjectRepository;
    private ProjectStatusRepository projectStatusRepository;
    private CategoryRepository categoryRepository;
    private ProjectByUserDonateMapper projectByUserDonateMapper;
    private NewProjectMapper newProjectMapper;
    private EditedProjectMapper editedProjectMapper;
    private RoleRepository roleRepository;
    private UserService userService;
    private EntityManagerFactory entityManagerFactory;
    private GalleryRepository galleryRepository;
    private UserRepository userRepository;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository,
                              CategoryService categoryService, PreviewProjectMapper previewProjectMapper,
                              MainProjectInfoMapper mainProjectInfoMapper,
                              DonatedUserProjectRepository donatedUserProjectRepository,
                              ProjectStatusRepository projectStatusRepository, CategoryRepository categoryRepository,
                              ProjectByUserDonateMapper projectByUserDonateMapper, NewProjectMapper newProjectMapper,
                              EditedProjectMapper editedProjectMapper, RoleRepository roleRepository,
                              UserService userService, EntityManagerFactory entityManagerFactory, GalleryRepository galleryRepository,
                              UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.categoryService = categoryService;
        this.previewProjectMapper = previewProjectMapper;
        this.mainProjectInfoMapper = mainProjectInfoMapper;
        this.donatedUserProjectRepository = donatedUserProjectRepository;
        this.projectStatusRepository = projectStatusRepository;
        this.categoryRepository = categoryRepository;
        this.projectByUserDonateMapper = projectByUserDonateMapper;
        this.newProjectMapper = newProjectMapper;
        this.editedProjectMapper = editedProjectMapper;
        this.roleRepository = roleRepository;
        this.userService = userService;
        this.entityManagerFactory = entityManagerFactory;
        this.galleryRepository = galleryRepository;
        this.userRepository = userRepository;
    }

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
            (List<Long> categoryIds, Long statusId, Long moneyFrom, Long moneyTo, Long ownerId, Long moderatorId, Pageable pageable) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Project> projectCriteria = builder.createQuery(Project.class);
        Root<Project> root = projectCriteria.from(Project.class);
        List<Predicate> predicates = new ArrayList<>();

        predicates.add(builder.greaterThan(root.get("projectStatus").get("id"), 3));
        if (moneyFrom != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("moneyNeeded"), moneyFrom));
        }
        if (moneyTo != null) {
            predicates.add(builder.lessThanOrEqualTo(root.get("moneyNeeded"), moneyTo));
        }
        if (statusId != null && statusId > 3) {
            predicates.add(builder.equal(root.get("projectStatus").get("id"), statusId));
        }
        if (categoryIds != null) {
            categoryService.getCategoriesByIds(categoryIds).forEach(category ->
                    predicates.add(builder.isMember(category, root.get("categories"))));
        }
        if (ownerId != null) {
            predicates.add(builder.equal(root.get("owner").get("id"), ownerId));
        }
        if (moderatorId != null) {
            predicates.add(builder.isMember(userRepository.getOne(moderatorId), root.get("moderators")));
        }

        projectCriteria.select(root).where(predicates.toArray(new Predicate[]{}));

        TypedQuery<Project> typedQuery = entityManager.createQuery(projectCriteria);
        typedQuery.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        typedQuery.setMaxResults(pageable.getPageSize());

        List<PreviewProjectDto> result = previewProjectMapper.convertListToDto(typedQuery.getResultList());

        transaction.commit();
        entityManager.close();

        return result;
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

    @Override
    @Transactional
    public NewProjectDto saveProject(NewProjectDto projectDto, long userId) {
        Project projectModel = createProjectModelFromDtoData(projectDto, userId);
        projectModel.setCategories(getValidCategoriesFromCategoriesDto(projectDto.getCategories()));
        Project resultOfQuery = projectRepository.save(projectModel);
        Gallery gallery = new Gallery();
        gallery.setProject(resultOfQuery);
        resultOfQuery.setGallery(galleryRepository.saveAndFlush(gallery));
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
        projectModel.setCreationDate(new Timestamp(222));
        projectModel.setProjectStatus(projectStatusRepository.getOne(1L));
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
