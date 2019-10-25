package com.softserve.ita.java442.cityDonut.service.impl;

import com.softserve.ita.java442.cityDonut.dto.status.ProjectStatusDTO;
import com.softserve.ita.java442.cityDonut.mapper.status.ProjectStatusMapper;
import com.softserve.ita.java442.cityDonut.repository.ProjectStatusRepository;
import com.softserve.ita.java442.cityDonut.service.ProjectStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectStatusImpl implements ProjectStatusService {

    @Autowired
    ProjectStatusRepository projectStatusRepository;

    @Override
    public ProjectStatusDTO getInitialStatus() {
        return ProjectStatusMapper.convertToDto(projectStatusRepository.getOne(1l));
    }
}
