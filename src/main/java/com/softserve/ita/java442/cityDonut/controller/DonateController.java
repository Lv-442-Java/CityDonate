package com.softserve.ita.java442.cityDonut.controller;

import com.softserve.ita.java442.cityDonut.dto.donateDto.DonatesForProjectDto;
import com.softserve.ita.java442.cityDonut.dto.project.ProjectByUserDonateDto;
import com.softserve.ita.java442.cityDonut.service.DonateService;
import com.softserve.ita.java442.cityDonut.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/donate")

public class DonateController {

    @Autowired
    private DonateService donateService;

    @Autowired
    private ProjectService projectService;

    @GetMapping("/project/{id}")
    public ResponseEntity<List<DonatesForProjectDto>> getProjectDonates(@PathVariable long id) {
        List<DonatesForProjectDto> donatesForProjectDtos = donateService.getProjectDonates(id);
        return new ResponseEntity<>(donatesForProjectDtos, HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<ProjectByUserDonateDto>> getDonatedUserProject(@PathVariable long id) {
        List<ProjectByUserDonateDto> projectByUserDonateDtos = projectService.getDonatedUserProject(id);
        return new  ResponseEntity<>(projectByUserDonateDtos, HttpStatus.OK);
    }
}
