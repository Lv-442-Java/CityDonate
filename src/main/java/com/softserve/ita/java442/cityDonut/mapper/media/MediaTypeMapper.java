package com.softserve.ita.java442.cityDonut.mapper.media;

import com.softserve.ita.java442.cityDonut.dto.media.MediaTypeDto;
import com.softserve.ita.java442.cityDonut.mapper.GeneralMapper;
import com.softserve.ita.java442.cityDonut.model.MediaType;

public class MediaTypeMapper implements GeneralMapper<MediaType, MediaTypeDto> {
    @Override
    public MediaTypeDto convertToDto(MediaType model) {
        return MediaTypeDto.builder()
                .id(model.getId())
                .type(model.getType())
                .build();
    }

    @Override
    public MediaType convertToModel(MediaTypeDto dto) {
        return MediaType.builder()
                .id(dto.getId())
                .type(dto.getType())
                .build();
    }
}
