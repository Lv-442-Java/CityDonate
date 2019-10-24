package com.softserve.ita.java442.cityDonut.service;

import com.softserve.ita.java442.cityDonut.dto.project.MainProjectInfoDto;

public interface ProjectService {
    MainProjectInfoDto getProjectById(long id);
}
