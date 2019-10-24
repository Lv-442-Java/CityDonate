package com.softserve.ita.java442.cityDonut.controller;

import com.softserve.ita.java442.cityDonut.dto.project.MainProjectInfoDto;
import com.softserve.ita.java442.cityDonut.dto.project.PreviewProjectDto;
import com.softserve.ita.java442.cityDonut.service.impl.ProjectServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/project")
public class ProjectController {

    @Autowired
    private ProjectServiceImpl projectService;

    @GetMapping("/{id}")
    public ResponseEntity<MainProjectInfoDto> getProjectById(@PathVariable long id) {
        return new ResponseEntity<>(projectService.getProjectById(id), HttpStatus.OK);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<PreviewProjectDto>> filter(
            @RequestParam List<String> categories, long moneyFrom, long moneyTo, String status) {
        return new ResponseEntity<>(
                projectService.getFilteredProjects(categories, moneyFrom, moneyTo, status), HttpStatus.OK);
    }

}
