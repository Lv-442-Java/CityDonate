package com.softserve.ita.java442.cityDonut.controller;

import com.softserve.ita.java442.cityDonut.dto.donateDto.DonateDto;
import com.softserve.ita.java442.cityDonut.service.DonateService;
import com.softserve.ita.java442.cityDonut.service.impl.DonateServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/donate")
public class DonateController {

    @Autowired
    DonateServiceImpl donateService;

    @GetMapping("/project/{id}")
    public List<DonateDto> getSumProjectDonate(@PathVariable long id) {
        return donateService.getSumProjectDonate(id);
    }
}
