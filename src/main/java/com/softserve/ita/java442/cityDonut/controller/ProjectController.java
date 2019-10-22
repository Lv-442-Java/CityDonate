package com.softserve.ita.java442.cityDonut.controller;

import com.softserve.ita.java442.cityDonut.dto.ProjectDto;
import com.softserve.ita.java442.cityDonut.dto.ProjectStatusDto;
import com.softserve.ita.java442.cityDonut.service.ProjectService;
import com.softserve.ita.java442.cityDonut.service.ProjectStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ProjectStatusService projectStatusService;

    @PostMapping("/")
    ProjectDto newProject(@RequestBody ProjectDto project) {

        //Do something with fields...


        //Set creation date to now
        project.setCreationDate(LocalDateTime.now());

        //Set project status to 'not started'
        //Get initial project status from project status service
        project.setProjectStatusDto(new ProjectStatusDto());

        //Add project to the database using service

        //Set user owner to project

        //return project with new {id}
        return project;
    }

}
