package com.softserve.ita.java442.cityDonut.service.impl;

import com.softserve.ita.java442.cityDonut.dto.donateDto.DonatesForProjectDto;
import com.softserve.ita.java442.cityDonut.mapper.donate.DonateForProjectMapper;
import com.softserve.ita.java442.cityDonut.model.Donate;
import com.softserve.ita.java442.cityDonut.model.Project;
import com.softserve.ita.java442.cityDonut.repository.DonateRepository;
import com.softserve.ita.java442.cityDonut.service.DonateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;


@Service
public class DonateServiceImpl implements DonateService {

    @Autowired
    private DonateRepository donateRepository;


    @Override
    public List<DonatesForProjectDto> getProjectDonates(long id) {
        List<Donate> donates = donateRepository.getByProjectId(id);
        List<DonatesForProjectDto> donatesForProjectDtos = new LinkedList<>();
        for (Donate donate : donates) {
            donatesForProjectDtos.add(new DonateForProjectMapper().convertToDto(donate));
        }
        return donatesForProjectDtos;
    }

    public List<Project> getProjectByUserDonate(long id) {

        return null;
    }
}
