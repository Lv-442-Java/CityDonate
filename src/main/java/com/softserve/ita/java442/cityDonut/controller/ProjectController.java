package com.softserve.ita.java442.cityDonut.controller;

import com.softserve.ita.java442.cityDonut.dto.project.MainProjectInfoDto;
import com.softserve.ita.java442.cityDonut.service.impl.ProjectServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/project")
public class ProjectController {

    @Autowired
    ProjectServiceImpl projectService;

    @GetMapping("/{id}")
    public ResponseEntity<MainProjectInfoDto> getProjectById(@PathVariable long id) {
        return new ResponseEntity<>(projectService.getProjectById(id), HttpStatus.OK);
    }
}
