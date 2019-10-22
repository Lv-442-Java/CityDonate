package com.softserve.ita.java442.cityDonut.mapper;

import com.softserve.ita.java442.cityDonut.dto.MediaDto;
import com.softserve.ita.java442.cityDonut.model.Media;
import org.modelmapper.ModelMapper;

public class MediaMapper {

    public static MediaDto convertToDto(Media media) {
        return new ModelMapper().map(media, MediaDto.class);
    }

    public static Media convertToModel(MediaDto mediaDto) {
        return new ModelMapper().map(mediaDto, Media.class);
    }

}
