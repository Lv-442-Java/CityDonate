package com.softserve.ita.java442.cityDonut.mapper.media;

import com.softserve.ita.java442.cityDonut.dto.media.MediaDto;
import com.softserve.ita.java442.cityDonut.mapper.GeneralMapper;
import com.softserve.ita.java442.cityDonut.model.Media;
import com.softserve.ita.java442.cityDonut.repository.GalleryRepository;
import com.softserve.ita.java442.cityDonut.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MediaMapper implements GeneralMapper<Media, MediaDto> {

    @Autowired
    private GalleryRepository galleryRepository;

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
                .mediaType(model.getMediaType())
                .galleryId(model.getGallery().getId())
                .creationDate(model.getCreationDate())
                .build();
    }

    @Override
    public Media convertToModel(MediaDto dto) {
        return Media.builder()
                .id(dto.getId())
                .name(dto.getName())
                .extension(dto.getExtension())
                .fileId(dto.getFileId())
                .mediaType(dto.getMediaType())
                .gallery(galleryRepository.getOne(dto.getGalleryId()))
                .build();
    }
}
