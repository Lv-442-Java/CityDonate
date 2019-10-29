package com.softserve.ita.java442.cityDonut.service;

import com.softserve.ita.java442.cityDonut.dto.project.MainProjectInfoDto;

import com.softserve.ita.java442.cityDonut.dto.project.EditedProjectDto;
import com.softserve.ita.java442.cityDonut.dto.project.NewProjectDto;

public interface ProjectService {
    MainProjectInfoDto getProjectById(long id);

    NewProjectDto saveProject(NewProjectDto project, long userId);

    EditedProjectDto editProject(EditedProjectDto project, long projectId, long userId);
}
