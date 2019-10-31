package com.softserve.ita.java442.cityDonut.controller;

import com.softserve.ita.java442.cityDonut.dto.fieldsCheck.FieldsCheckDto;
import com.softserve.ita.java442.cityDonut.dto.project.*;
import com.softserve.ita.java442.cityDonut.service.FieldsCheckService;
import com.softserve.ita.java442.cityDonut.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private FieldsCheckService fieldsCheckService;

    @GetMapping("/project/{id}")
    public ResponseEntity<MainProjectInfoDto> getProjectById(@PathVariable long id) {
        return new ResponseEntity<>(projectService.getProjectById(id), HttpStatus.OK);
    }

    @GetMapping("/project/filter")
    public ResponseEntity<List<PreviewProjectDto>> filter(
            @RequestParam List<Long> categories, long status, long moneyFrom, long moneyTo,
            Pageable pageable) {
        return new ResponseEntity<>(
                projectService.getFilteredProjects(categories, status, moneyFrom, moneyTo, pageable), HttpStatus.OK);
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
                projectService.saveProject(project, /*user id*/ 1L),
                HttpStatus.OK
        );
    }

    @PutMapping("/project/{projectId}")
    public ResponseEntity<EditedProjectDto> editProject(@RequestBody EditedProjectDto project, @PathVariable long projectId) {
        return new ResponseEntity<>(
                projectService.editProject(project, projectId, /*user id*/ 1L),
                HttpStatus.OK
        );
    }

    @GetMapping("/projects/free")
    public ResponseEntity<List<ProjectInfoDto>> getAllFreeProjects() {
        return new ResponseEntity<>(projectService.getFreeProject(), HttpStatus.OK);
    }

    @PutMapping("/project/{id}/addToModerate/{moderator_id}")
    public ResponseEntity<MainProjectInfoDto> setModeratorToProject(@PathVariable("id") long id, @PathVariable("moderator_id") long moderatorId) {
        return new ResponseEntity<>(projectService.addModeratorToProject(id, moderatorId), HttpStatus.OK);
    }

}
