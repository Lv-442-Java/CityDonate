package com.softserve.ita.java442.cityDonut.service.impl;

import com.softserve.ita.java442.cityDonut.constant.ErrorMessage;
import com.softserve.ita.java442.cityDonut.dto.project.MainProjectInfoDto;
import com.softserve.ita.java442.cityDonut.exception.NotFoundException;
import com.softserve.ita.java442.cityDonut.mapper.project.MainProjectInfoMapper;
import com.softserve.ita.java442.cityDonut.dto.project.EditedProjectDTO;
import com.softserve.ita.java442.cityDonut.dto.project.NewProjectDTO;
import com.softserve.ita.java442.cityDonut.mapper.project.EditedProjectMapper;
import com.softserve.ita.java442.cityDonut.mapper.project.NewProjectMapper;
import com.softserve.ita.java442.cityDonut.mapper.user.UserMapper;
import com.softserve.ita.java442.cityDonut.model.Project;
import com.softserve.ita.java442.cityDonut.model.User;
import com.softserve.ita.java442.cityDonut.repository.ProjectRepository;
import com.softserve.ita.java442.cityDonut.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    MainProjectInfoMapper mainProjectInfoMapper;

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
    public NewProjectDTO saveProject(NewProjectDTO project) {
        Project projectModel = NewProjectMapper.convertToModel(project);
        Project resultOfQuery = projectRepository.save(projectModel);
        NewProjectDTO result = NewProjectMapper.convertToDTO(resultOfQuery);
        projectRepository.flush();
        return result;
    }

    @Override
    public EditedProjectDTO editProject(EditedProjectDTO project) {
        Project projectModel = projectRepository.findById(project.getId()).get();
        projectModel.setName(project.getName());
        projectModel.setDescription(project.getDescription());
        projectModel.setLocation(project.getLocation());
        projectModel.setLocationLatitude(project.getLocation());
        projectModel.setLocationLongitude(project.getLocationLongitude());

        Project resultOfQuery = projectRepository.save(projectModel);
        EditedProjectDTO result = EditedProjectMapper.convertToDTO(resultOfQuery);
        projectRepository.flush();
        return result;
    }

    @Override
    public NewProjectDTO getProjectByUserAndProjectId(long userId, long id) {
        Project project = projectRepository.findByOwnerAndId(User.builder().id(userId).build(), id);
        return NewProjectMapper.convertToDTO(project);
    }
}
