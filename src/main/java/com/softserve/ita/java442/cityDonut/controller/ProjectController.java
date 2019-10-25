package com.softserve.ita.java442.cityDonut.controller;

import com.softserve.ita.java442.cityDonut.dto.ProjectDto;
import com.softserve.ita.java442.cityDonut.dto.ProjectStatusDto;
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

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ProjectStatusService projectStatusService;

    @GetMapping("/")
    String temp() {return "success get";}

    @PostMapping("/")
    NewProjectDTO newProject(@RequestBody NewProjectDTO project) {

        System.out.println("IN PROJECT CONTROLLER");

        //Do something with fields...

        //Set creation date to now
        project.setCreationDate(LocalDateTime.now());

        //Set project status to 'not started'
        //Get initial project status from project status service
        project.setProjectStatusDTO(projectStatusService.getInitialStatus());

        //Set user owner to project
        long userId = 1l;
        project.setOwnerDTO(new UserDTO(userId));

        //Add project to the database using service
        NewProjectDTO generaredProject = projectService.saveProject(project);

        //return project with new {id}
        return generaredProject;
    }

    @PutMapping("/{id}")
    EditedProjectDTO editProject(@RequestBody EditedProjectDTO project, @PathVariable long id) {

        //get user id
        long userId = 1l;

        //get project by id and user id
        NewProjectDTO oldProject = projectService.getProjectByUserAndProjectId(userId, id);

        //exceprion if oldProject == null
        if (oldProject == null) return null;

        //set fields to the project
        project.setId(id);

        //Save project
        EditedProjectDTO savedProject = projectService.editProject(project);

        return savedProject;
    }

}
