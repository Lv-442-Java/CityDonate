package com.softserve.ita.java442.cityDonut.service;

import com.softserve.ita.java442.cityDonut.dto.project.MainProjectInfoDto;
import com.softserve.ita.java442.cityDonut.dto.project.PreviewProjectDto;
import com.softserve.ita.java442.cityDonut.dto.project.ProjectByUserDonateDto;

import java.util.List;

import com.softserve.ita.java442.cityDonut.dto.project.EditedProjectDto;
import com.softserve.ita.java442.cityDonut.dto.project.NewProjectDto;

public interface ProjectService {
    MainProjectInfoDto getProjectById(long id);
    NewProjectDto saveProject(NewProjectDto project, long userId);
    EditedProjectDto editProject(EditedProjectDto project, long projectId, long userId);
    List<PreviewProjectDto> getFilteredProjects(List<Long> categoryIds, long moneyFrom, long moneyTo, Long statusId);
    List<ProjectByUserDonateDto> getDonatedUserProject(long id);
}
