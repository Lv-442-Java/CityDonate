package com.softserve.ita.java442.cityDonut.service.impl;

import com.softserve.ita.java442.cityDonut.constant.ErrorMessage;
import com.softserve.ita.java442.cityDonut.dto.donateDto.DonatesForProjectDto;
import com.softserve.ita.java442.cityDonut.exception.NotFoundException;
import com.softserve.ita.java442.cityDonut.mapper.donate.DonateForProjectMapper;
import com.softserve.ita.java442.cityDonut.model.Donate;
import com.softserve.ita.java442.cityDonut.repository.DonateRepository;
import com.softserve.ita.java442.cityDonut.repository.ProjectRepository;
import com.softserve.ita.java442.cityDonut.service.DonateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;


@Service
public class DonateServiceImpl implements DonateService {

    @Autowired
    private DonateRepository donateRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public List<DonatesForProjectDto> getProjectDonates(long id, Pageable pageable) {
        if(!projectExists(id)) {
            throw new NotFoundException(ErrorMessage.PROJECT_NOT_FOUND_BY_ID);
        }
        List<Donate> donates = donateRepository.getByProjectId(id, pageable);
        List<DonatesForProjectDto> donatesForProjectDtos = new LinkedList<>();
        for (Donate donate : donates) {
            donatesForProjectDtos.add(new DonateForProjectMapper().convertToDto(donate));
        }
        return donatesForProjectDtos;
    }

    @Override
    public List<DonatesForProjectDto> getUserDonatesByProject(long id, long userId, Pageable pageable) {
        if(!projectExists(id)) {
            throw new NotFoundException(ErrorMessage.PROJECT_NOT_FOUND_BY_ID);
        }
        List<Donate> donates = donateRepository.getByProjectIdAndUserId(id, userId, pageable);
        List<DonatesForProjectDto> donatesForProjectDtos = new LinkedList<>();
        for (Donate donate : donates) {
            donatesForProjectDtos.add(new DonateForProjectMapper().convertToDto(donate));
        }
        return donatesForProjectDtos;
    }

    private boolean projectExists(long id) {
        return projectRepository.existsById(id);
    }
}
