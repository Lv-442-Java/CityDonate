package com.softserve.ita.java442.cityDonut.controller;

import com.softserve.ita.java442.cityDonut.dto.project.PreviewProjectDto;
import com.softserve.ita.java442.cityDonut.service.impl.ProjectServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/project")
public class ProjectController {

    @Autowired
    private ProjectServiceImpl projectService;

    @GetMapping("/filter")
    List<PreviewProjectDto> filter(@RequestParam List<String> categories, long moneyFrom, long moneyTo, String status) {
        return projectService.getFilteredProjects(categories, moneyFrom, moneyTo, status);
    }

}
