package com.softserve.ita.java442.cityDonut.service;

import com.softserve.ita.java442.cityDonut.dto.project.*;

import java.util.List;

public interface ProjectService {
    MainProjectInfoDto getProjectById(long id);

    NewProjectDto saveProject(NewProjectDto project, long userId);

    EditedProjectDto editProject(EditedProjectDto project, long projectId, long userId);

    List<PreviewProjectDto> getFilteredProjects(List<Long> categoryIds, long moneyFrom, long moneyTo, Long statusId);

    List<ProjectByUserDonateDto> getDonatedUserProject(long id);
}
