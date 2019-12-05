package com.softserve.ita.java442.cityDonut.service;

import com.softserve.ita.java442.cityDonut.dto.projectStatus.ProjectStatusDto;
import com.softserve.ita.java442.cityDonut.model.ProjectStatus;

import java.util.List;

public interface ProjectStatusService {
    ProjectStatus getById(Long id);

    List<ProjectStatusDto> getStatusesAfterValidation();

    List<ProjectStatusDto> getAllStatuses();
}
