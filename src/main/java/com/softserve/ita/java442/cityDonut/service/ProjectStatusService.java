package com.softserve.ita.java442.cityDonut.service;

import com.softserve.ita.java442.cityDonut.model.ProjectStatus;

public interface ProjectStatusService {
    ProjectStatus getByStatus(String status);
}
