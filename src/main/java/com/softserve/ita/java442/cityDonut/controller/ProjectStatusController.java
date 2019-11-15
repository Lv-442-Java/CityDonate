package com.softserve.ita.java442.cityDonut.controller;

import com.softserve.ita.java442.cityDonut.dto.projectStatus.ProjectStatusDto;
import com.softserve.ita.java442.cityDonut.service.ProjectStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/status/")
public class ProjectStatusController {

    @Autowired
    ProjectStatusService projectStatusService;

    @GetMapping("/afterValidation")
    public ResponseEntity<List<ProjectStatusDto>> getStatusesAfterValidation() {
        return new ResponseEntity<>(projectStatusService.getStatusesAfterValidation(), HttpStatus.OK);
    }
}
