package com.softserve.ita.java442.cityDonut.mapper.lastChatUpdated;

import com.softserve.ita.java442.cityDonut.dto.lastChatUpdated.LastChatUpdatedDto;
import com.softserve.ita.java442.cityDonut.mapper.GeneralMapper;
import com.softserve.ita.java442.cityDonut.model.LastChatUpdated;
import com.softserve.ita.java442.cityDonut.model.Project;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class LastChatUpdatedMapper implements GeneralMapper <LastChatUpdated, LastChatUpdatedDto> {

    @Override
    public LastChatUpdatedDto convertToDto(LastChatUpdated model) {
        return LastChatUpdatedDto.builder()
                .id(model.getId())
                .dateTime(model.getDateTime())
                .userId(model.getUser().getId())
                .projectId(model.getProject().getId())
                .build();
    }

    @Override
    public LastChatUpdated convertToModel(LastChatUpdatedDto dto) {
        return LastChatUpdated.builder()
                .id(dto.getId())
                .dateTime(dto.getDateTime())
                .build();
    }

    public List<LastChatUpdatedDto> convertListToDtos(List<LastChatUpdated> lastChatUpdatedList) {
        List<LastChatUpdatedDto> lastChatUpdatedDtoList = new ArrayList<>();
        lastChatUpdatedList.forEach((item) -> {
            lastChatUpdatedDtoList.add(convertToDto(item));
        });
        return lastChatUpdatedDtoList;
    }
}
