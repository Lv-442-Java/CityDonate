package com.softserve.ita.java442.cityDonut.controller;

import com.softserve.ita.java442.cityDonut.dto.donateDto.DonateDto;
import com.softserve.ita.java442.cityDonut.service.impl.DonateServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/donate")

public class DonateController {

    private final
    DonateServiceImpl donateService;

    public DonateController(DonateServiceImpl donateService) {
        this.donateService = donateService;
    }


    @GetMapping("/project/{id}")
    public ResponseEntity<List<DonateDto>> getProjectDonates(@PathVariable long id) {
        return new ResponseEntity<>(donateService.getProjectDonates(id), HttpStatus.OK);
    }

}
