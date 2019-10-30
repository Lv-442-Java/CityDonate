package com.softserve.ita.java442.cityDonut.service;

import com.softserve.ita.java442.cityDonut.dto.donateDto.DonatesForProjectDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DonateService {
    List<DonatesForProjectDto> getProjectDonates(long id, Pageable pageable);

    List<DonatesForProjectDto> getUserDonatesByProject(long id, long userId);
}
