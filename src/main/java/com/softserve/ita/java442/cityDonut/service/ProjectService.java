package com.softserve.ita.java442.cityDonut.service;

import com.softserve.ita.java442.cityDonut.dto.project.PreviewProjectDto;

import java.util.List;

public interface ProjectService {
    List<PreviewProjectDto> getFilteredProjects(List<String> categories, long moneyFrom, long moneyTo, String status);
}
