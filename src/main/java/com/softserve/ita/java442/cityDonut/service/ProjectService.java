package com.softserve.ita.java442.cityDonut.service;

import com.softserve.ita.java442.cityDonut.dto.project.MainProjectInfoDto;

import com.softserve.ita.java442.cityDonut.dto.project.EditedProjectDTO;
import com.softserve.ita.java442.cityDonut.dto.project.NewProjectDTO;

public interface ProjectService {
    MainProjectInfoDto getProjectById(long id);

    NewProjectDTO saveProject(NewProjectDTO project, long userId);

    EditedProjectDTO editProject(EditedProjectDTO project, long projectId, long userId);
}
