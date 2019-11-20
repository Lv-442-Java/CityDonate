package com.softserve.ita.java442.cityDonut.controller;

import com.softserve.ita.java442.cityDonut.dto.fieldsCheck.FieldsCheckDto;
import com.softserve.ita.java442.cityDonut.dto.project.*;
import com.softserve.ita.java442.cityDonut.security.UserPrincipal;
import com.softserve.ita.java442.cityDonut.service.FieldsCheckService;
import com.softserve.ita.java442.cityDonut.service.ProjectService;

import com.softserve.ita.java442.cityDonut.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ProjectController {
    private ProjectService projectService;

    private UserService userService;
    private FieldsCheckService fieldsCheckService;

    @Autowired
    public ProjectController(ProjectService projectService, UserService userService, FieldsCheckService fieldsCheckService) {
        this.projectService = projectService;
        this.userService = userService;
        this.fieldsCheckService = fieldsCheckService;
    }

    @GetMapping("/project/{id}")
    public ResponseEntity<MainProjectInfoDto> getProjectById(@PathVariable long id) {
        return new ResponseEntity<>(projectService.getProjectById(id), HttpStatus.OK);
    }

    @GetMapping("/project/filter")
    public ResponseEntity<List<PreviewProjectDto>> filter(
            @RequestParam(value = "categories", required = false) List<Long> categories,
            @RequestParam(value = "status",required = false) Long status,
            @RequestParam(value = "moneyFrom",required = false) Long moneyFrom,
            @RequestParam(value = "moneyTo",required = false) Long moneyTo,
            Pageable pageable) {
        return new ResponseEntity<>(
                projectService.getFilteredProjects(categories, status, moneyFrom, moneyTo, pageable), HttpStatus.OK);
    }

    @GetMapping("/maxMoney")
    public ResponseEntity<Long> getMaxMoneyNeeded() {
        return new ResponseEntity<>(projectService.getMaxMoneyNeeded(), HttpStatus.OK);
    }

    @GetMapping("/project/{id}/fields/valid")
    public ResponseEntity<List<FieldsCheckDto>> getFieldsValidationInfo(@PathVariable long id) {
        return new ResponseEntity<>(fieldsCheckService.getAllByProjectId(id), HttpStatus.OK);
    }

    @PutMapping("/project/field/valid/change")
    public ResponseEntity<FieldsCheckDto> updateFieldCheck(@RequestBody FieldsCheckDto fieldsCheckDto) {
        return new ResponseEntity<>(fieldsCheckService.update(fieldsCheckDto), HttpStatus.OK);
    }

    @GetMapping("/project")
    String temp() {
        return "success get";
    }

    @PostMapping("/project")
    public ResponseEntity<NewProjectDto> createProject(@Valid @RequestBody NewProjectDto project) {
        return new ResponseEntity<>(
                projectService.saveProject(project, userService.getCurrentUser().getId()),
                HttpStatus.OK
        );
    }

    @PutMapping("/project/{projectId}")
    public ResponseEntity<EditedProjectDto> editProject(@Valid @RequestBody EditedProjectDto project, @PathVariable long projectId, Authentication auth) {
        return new ResponseEntity<>(
                projectService.editProject(project, projectId, ((UserPrincipal)auth.getPrincipal()).getUser().getId()),
                HttpStatus.OK
        );
    }

    @GetMapping("/projects/free")
    public ResponseEntity<List<ProjectInfoDto>> getAllFreeProjects() {
        return new ResponseEntity<>(projectService.getFreeProject(), HttpStatus.OK);
    }

    @PutMapping("/project/{id}/addToModerate")
    public ResponseEntity<MainProjectInfoDto> setModeratorToProject(@PathVariable("id") long id) {
        return new ResponseEntity<>(projectService.addModeratorToProject(id), HttpStatus.OK);
    }
}
