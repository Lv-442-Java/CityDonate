package com.softserve.ita.java442.cityDonut.mapper.media;

import com.softserve.ita.java442.cityDonut.dto.media.MediaDto;
import com.softserve.ita.java442.cityDonut.mapper.GeneralMapper;
import com.softserve.ita.java442.cityDonut.model.Media;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MediaMapper implements GeneralMapper<Media, MediaDto> {

    public List<MediaDto> convertListToDto(List<Media> modelList) {
        List<MediaDto> dtoList = new ArrayList<>();
        for (Media model : modelList) {
            dtoList.add(convertToDto(model));
        }
        return dtoList;
    }

    public List<Media> convertListToModel(List<MediaDto> dtoList) {
        List<Media> modelList = new ArrayList<>();
        for (MediaDto dto : dtoList) {
            modelList.add(convertToModel(dto));
        }
        return modelList;
    }

    @Override
    public MediaDto convertToDto(Media model) {
        return MediaDto.builder()
                .id(model.getId())
                .name(model.getName())
                .extension(model.getExtension())
                .fileId(model.getFileId())
                .build();
    }

    @Override
    public Media convertToModel(MediaDto dto) {
        return Media.builder()
                .id(dto.getId())
                .name(dto.getName())
                .extension(dto.getExtension())
                .fileId(dto.getFileId())
                .build();
    }
}
