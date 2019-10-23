package com.softserve.ita.java442.cityDonut.service;

import com.softserve.ita.java442.cityDonut.dto.donateDto.DonateDto;

import java.util.List;

public interface DonateService {
    List<DonateDto> getSumProjectDonate(long id);
}
