package com.softserve.ita.java442.cityDonut.service.impl;

import com.softserve.ita.java442.cityDonut.constant.ErrorMessage;
import com.softserve.ita.java442.cityDonut.dto.project.MainProjectInfoDto;
import com.softserve.ita.java442.cityDonut.exception.NotFoundException;
import com.softserve.ita.java442.cityDonut.mapper.project.MainProjectInfoMapper;
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
}
