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

    private final
    DonateRepository donateRepository;

    public DonateServiceImpl(DonateRepository donateRepository) {
        this.donateRepository = donateRepository;
    }

    @Override
    public List<DonateDto> getProjectDonates(long id) {
        List<Donate> donates = donateRepository.getByProjectId(id);
        System.out.println(donates);
        List<DonateDto> donateDtos = new LinkedList<>();
        for (Donate donate: donates) {
            donateDtos.add(new DonateMapper().convertToDto(donate));
        }
        return donateDtos;
    }
}
