package com.softserve.ita.java442.cityDonut.controller;

import com.softserve.ita.java442.cityDonut.dto.project.MainProjectInfoDto;
import com.softserve.ita.java442.cityDonut.dto.project.EditedProjectDto;
import com.softserve.ita.java442.cityDonut.dto.project.NewProjectDto;
import com.softserve.ita.java442.cityDonut.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @GetMapping("/{id}")
    public ResponseEntity<MainProjectInfoDto> getProjectById(@PathVariable long id) {
        return new ResponseEntity<>(projectService.getProjectById(id), HttpStatus.OK);
    }

    @GetMapping("/")
    String temp() {return "success get";}

    @PostMapping("/")
    public ResponseEntity<NewProjectDto> createProject(@Valid @RequestBody NewProjectDto project) {
        return new ResponseEntity<>(
                projectService.saveProject(project, /*user id*/ 1L),
                HttpStatus.OK
        );
    }

    @PutMapping("/{projectId}")
    public ResponseEntity<EditedProjectDto> editProject(@RequestBody EditedProjectDto project, @PathVariable long projectId) {
        return new ResponseEntity<>(
                projectService.editProject(project, projectId, /*user id*/ 1L),
                HttpStatus.OK
        );
    }

}
