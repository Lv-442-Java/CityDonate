package com.softserve.ita.java442.cityDonut.controller;

import com.softserve.ita.java442.cityDonut.dto.donateDto.DonatesForProjectDto;
import com.softserve.ita.java442.cityDonut.dto.project.ProjectByUserDonateDto;
import com.softserve.ita.java442.cityDonut.service.DonateService;
import com.softserve.ita.java442.cityDonut.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/donates")
public class DonateController {

    @Autowired
    private DonateService donateService;

    @Autowired
    private ProjectService projectService;

    @GetMapping("/project/{id}")
    public ResponseEntity<List<DonatesForProjectDto>> getProjectDonates(@PathVariable long id,
                                                                        @PageableDefault(sort = {"date", "sum"},
                                                                                direction = Sort.Direction.DESC)
                                                                                Pageable pageable) {
        List<DonatesForProjectDto> donatesForProjectDtos = donateService.getProjectDonates(id, pageable);
        return new ResponseEntity<>(donatesForProjectDtos, HttpStatus.OK);
    }

    @GetMapping("/project/{id}/user/{userId}")
    public ResponseEntity<List<DonatesForProjectDto>> getUserDonatesByProject(@PathVariable long id, @PathVariable long userId) {
        List<DonatesForProjectDto> donatesForProjectDtos = donateService.getUserDaontesByProject(id, userId);
        return new ResponseEntity<>(donatesForProjectDtos, HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<ProjectByUserDonateDto>> getDonatedUserProject(@PathVariable long id) {
        List<ProjectByUserDonateDto> projectByUserDonateDtos = projectService.getDonatedUserProject(id);
        return new ResponseEntity<>(projectByUserDonateDtos, HttpStatus.OK);
    }
}
