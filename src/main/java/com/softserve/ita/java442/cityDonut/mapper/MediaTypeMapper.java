package com.softserve.ita.java442.cityDonut.mapper;

import com.softserve.ita.java442.cityDonut.dto.MediaTypeDto;
import com.softserve.ita.java442.cityDonut.model.MediaType;
import org.modelmapper.ModelMapper;

public class MediaTypeMapper {
    public static MediaTypeDto convertToDto(MediaType mediaType) {
        return new ModelMapper().map(mediaType, MediaTypeDto.class);
    }

    public static MediaType convertToModel(MediaTypeDto mediaTypeDto) {
        return new ModelMapper().map(mediaTypeDto, MediaType.class);
    }
}
