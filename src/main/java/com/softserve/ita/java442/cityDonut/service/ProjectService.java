package com.softserve.ita.java442.cityDonut.service;

import com.softserve.ita.java442.cityDonut.dto.project.*;
import com.softserve.ita.java442.cityDonut.model.ProjectStatus;

import java.util.List;

public interface ProjectService {
    MainProjectInfoDto getProjectById(long id);

    NewProjectDto saveProject(NewProjectDto project, long userId);

    EditedProjectDto editProject(EditedProjectDto project, long projectId, long userId);

    List<PreviewProjectDto> getFilteredProjects(List<String> categories, long moneyFrom, long moneyTo, String status);

    List<ProjectByUserDonateDto> getDonatedUserProject(long id);

    List<ProjectInfoDto> getFreeProject();

    MainProjectInfoDto addModeratorToProject(long project_id, long moderator_id);
}
