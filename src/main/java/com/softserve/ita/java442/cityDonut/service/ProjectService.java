package com.softserve.ita.java442.cityDonut.service;

import com.softserve.ita.java442.cityDonut.dto.project.*;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProjectService {
    MainProjectInfoDto getProjectById(long id);

    NewProjectDto saveProject(NewProjectDto project, long userId);

    EditedProjectDto editProject(EditedProjectDto project, long projectId, long userId);

    List<PreviewProjectDto> getFilteredProjects(List<Long> categoryIds, long statusId, long moneyFrom, long moneyTo, Pageable pageable);

    List<ProjectByUserDonateDto> getDonatedUserProject(long id, Pageable pageable);

    List<ProjectInfoDto> getFreeProject();

    MainProjectInfoDto addModeratorToProject(long project_id);

}
