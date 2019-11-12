package com.softserve.ita.java442.cityDonut.controller;

import com.softserve.ita.java442.cityDonut.dto.donateDto.DonateDto;
import com.softserve.ita.java442.cityDonut.dto.donateDto.DonatesForProjectDto;
import com.softserve.ita.java442.cityDonut.dto.project.ProjectByUserDonateDto;
import com.softserve.ita.java442.cityDonut.mapper.donate.DonateMapper;
import com.softserve.ita.java442.cityDonut.repository.DonateRepository;
import com.softserve.ita.java442.cityDonut.service.DonateService;
import com.softserve.ita.java442.cityDonut.service.ProjectService;
import com.softserve.ita.java442.cityDonut.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/donates")
public class DonateController {

    @Autowired
    private DonateService donateService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;

    @GetMapping("/projects/{id}")
    public ResponseEntity<List<DonatesForProjectDto>> getProjectDonates(@PathVariable long id,
                                                                        @PageableDefault(sort = {"date"},
                                                                                direction = Sort.Direction.DESC)
                                                                                Pageable pageable) {
        List<DonatesForProjectDto> donatesForProjectDtos = donateService.getProjectDonates(id, pageable);
        return new ResponseEntity<>(donatesForProjectDtos, HttpStatus.OK);
    }

    @GetMapping("/projects/{id}/users/")
    public ResponseEntity<List<DonatesForProjectDto>> getUserDonatesByProject(@PathVariable long id,
                                                                              @PageableDefault(sort = {"date"},
                                                                                      direction = Sort.Direction.DESC)
                                                                                      Pageable pageable) {
        List<DonatesForProjectDto> donatesForProjectDtos = donateService.getUserDonatesByProject(id, userService.getCurrentUser().getId(), pageable);
        return new ResponseEntity<>(donatesForProjectDtos, HttpStatus.OK);
    }

    @GetMapping("/projects/users/")
    public ResponseEntity<List<ProjectByUserDonateDto>> getDonatedUserProject(@PageableDefault(sort = {"id"},
                                                                                    direction = Sort.Direction.ASC)
                                                                                    Pageable pageable) {
        List<ProjectByUserDonateDto> projectByUserDonateDtos = projectService.getDonatedUserProject(userService.getCurrentUser().getId(), pageable);
        return new ResponseEntity<>(projectByUserDonateDtos, HttpStatus.OK);
    }

    @GetMapping("all/projects/{id}")
    public ResponseEntity<Long> getDonatesSumByProjectId(@PathVariable long id) {
        return new ResponseEntity<> (donateService.getDonatesSumByProjectId(id), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<DonateDto> createDonate(@RequestBody DonateDto donateDto) {
        donateDto.setUserId(userService.getCurrentUser().getId());
        return new ResponseEntity<>(donateService.createDonate(donateDto),HttpStatus.OK);
    }
}
