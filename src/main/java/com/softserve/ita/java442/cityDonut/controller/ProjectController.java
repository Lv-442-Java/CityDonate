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
import com.softserve.ita.java442.cityDonut.dto.project.EditedProjectDTO;
import com.softserve.ita.java442.cityDonut.dto.project.NewProjectDTO;
import com.softserve.ita.java442.cityDonut.dto.user.UserDTO;
import com.softserve.ita.java442.cityDonut.service.ProjectService;
import com.softserve.ita.java442.cityDonut.service.ProjectStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/project")
public class ProjectController {

    //@Autowired
    //ProjectServiceImpl projectService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ProjectStatusService projectStatusService;

    @GetMapping("/{id}")
    public ResponseEntity<MainProjectInfoDto> getProjectById(@PathVariable long id) {
        return new ResponseEntity<>(projectService.getProjectById(id), HttpStatus.OK);
    }

    @GetMapping("/")
    String temp() {return "success get";}

    @PostMapping("/")
    public ResponseEntity<NewProjectDTO> createProject(@RequestBody NewProjectDTO project) {
        return new ResponseEntity<>(
                projectService.saveProject(project, /*user id*/ 1l),
                HttpStatus.OK
        );
    }

    @PutMapping("/{projectId}")
    public ResponseEntity<EditedProjectDTO> editProject(@RequestBody EditedProjectDTO project, @PathVariable long projectId) {
        return new ResponseEntity<>(
                projectService.editProject(project, projectId, /*user id*/ 1l),
                HttpStatus.OK
        );
    }

}
