package com.softserve.ita.java442.cityDonut.service.impl;

import com.softserve.ita.java442.cityDonut.dto.donateDto.DonateDto;
import com.softserve.ita.java442.cityDonut.mapper.GeneralMapper;
import com.softserve.ita.java442.cityDonut.mapper.donate.DonateMapper;
import com.softserve.ita.java442.cityDonut.model.Donate;
import com.softserve.ita.java442.cityDonut.model.Project;
import com.softserve.ita.java442.cityDonut.repository.DonateRepository;
import com.softserve.ita.java442.cityDonut.repository.ProjectRepository;
import com.softserve.ita.java442.cityDonut.service.DonateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class DonateServiceImpl implements DonateService {

    @Autowired
    DonateRepository donateRepository;

    @Override
    public List<DonateDto> getProjectDonates(long id) {
        List<Donate> donates = donateRepository.getByProjectId(id);
        List<DonateDto> donateDtos = new LinkedList<>();
        for (Donate donate: donates) {
            donateDtos.add(new DonateMapper().convertToDto(donate));
        }
        return donateDtos;
    }
}
