package com.softserve.ita.java442.cityDonut.service.impl;

import com.softserve.ita.java442.cityDonut.dto.project.MainProjectInfoDto;
import com.softserve.ita.java442.cityDonut.mapper.project.MainProjectInfoMapper;
import com.softserve.ita.java442.cityDonut.repository.ProjectRepository;
import com.softserve.ita.java442.cityDonut.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    ProjectRepository projectRepository;

    @Override
    public MainProjectInfoDto getProjectById(long id) {
        MainProjectInfoMapper mainProjectInfoMapper = new MainProjectInfoMapper();
        return mainProjectInfoMapper.convertToDto(projectRepository.getById(id));
    }
}
