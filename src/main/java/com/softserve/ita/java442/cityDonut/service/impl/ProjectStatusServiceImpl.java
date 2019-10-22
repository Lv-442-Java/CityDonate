package com.softserve.ita.java442.cityDonut.service.impl;

import com.softserve.ita.java442.cityDonut.repository.ProjectStatusRepository;
import com.softserve.ita.java442.cityDonut.service.ProjectStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectStatusServiceImpl implements ProjectStatusService {

    @Autowired
    ProjectStatusRepository projectStatusRepository;
}
