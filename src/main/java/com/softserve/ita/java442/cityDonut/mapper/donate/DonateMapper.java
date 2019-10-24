package com.softserve.ita.java442.cityDonut.mapper.donate;

import com.softserve.ita.java442.cityDonut.dto.donateDto.DonateDto;
import com.softserve.ita.java442.cityDonut.mapper.GeneralMapper;
import com.softserve.ita.java442.cityDonut.model.Donate;

public class DonateMapper implements GeneralMapper<Donate, DonateDto> {
    @Override
    public DonateDto convertToDto(Donate model) {
        return DonateDto.builder()
                .id(model.getId())
                .date(model.getDate())
                .sum(model.getSum())
                .userFirstName(model.getUser().getFirstName())
                .userLastName(model.getUser().getLastName())
                .build();
    }

    @Override
    public Donate convertToModel(DonateDto dto) {
        return null;
    }

//    public DonateDto convertToDto(Donate model, User user) {
//        return DonateDto.builder()
//                .id(model.getId())
//                .date(model.getDate())
//                .sum(model.getSum())
//                .userFirstName(user.getFirstName())
//                .userSecondName(user.getLastName())
//                .build();
//    }
}
