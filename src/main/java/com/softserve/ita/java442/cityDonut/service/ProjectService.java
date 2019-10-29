package com.softserve.ita.java442.cityDonut.service;

import com.softserve.ita.java442.cityDonut.dto.project.MainProjectInfoDto;
import com.softserve.ita.java442.cityDonut.dto.project.PreviewProjectDto;
import com.softserve.ita.java442.cityDonut.dto.project.ProjectByUserDonateDto;

import java.util.List;

public interface ProjectService {
    MainProjectInfoDto getProjectById(long id);
    List<PreviewProjectDto> getFilteredProjects(List<String> categories, long moneyFrom, long moneyTo, String status);
    List<ProjectByUserDonateDto> getDonatedUserProject(long id);
}
