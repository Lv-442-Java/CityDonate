package com.softserve.ita.java442.cityDonut.service.impl;

import com.softserve.ita.java442.cityDonut.dto.donateDto.DonateDto;
import com.softserve.ita.java442.cityDonut.repository.DonateRepository;
import com.softserve.ita.java442.cityDonut.service.DonateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DonateServiceImpl implements DonateService {

    @Autowired
    DonateRepository donateRepository;

    @Override
    public List<DonateDto> getSumProjectDonate(long id) {
        return null;
    }


}
