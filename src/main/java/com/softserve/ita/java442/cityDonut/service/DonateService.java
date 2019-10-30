package com.softserve.ita.java442.cityDonut.service;

import com.softserve.ita.java442.cityDonut.dto.donateDto.DonatesForProjectDto;

import java.util.List;

public interface DonateService {
    List<DonatesForProjectDto> getProjectDonates(long id);
}
