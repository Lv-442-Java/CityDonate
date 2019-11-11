package com.softserve.ita.java442.cityDonut.mapper.media;

import com.softserve.ita.java442.cityDonut.dto.media.MediaDto;
import com.softserve.ita.java442.cityDonut.mapper.GeneralMapper;
import com.softserve.ita.java442.cityDonut.model.Extension;
import com.softserve.ita.java442.cityDonut.model.Media;
import com.softserve.ita.java442.cityDonut.model.MediaType;
import com.softserve.ita.java442.cityDonut.repository.ExtensionRepository;
import com.softserve.ita.java442.cityDonut.repository.MediaTypeRepository;
import com.softserve.ita.java442.cityDonut.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MediaMapper implements GeneralMapper<Media, MediaDto> {

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private ExtensionRepository extensionRepository;
    @Autowired
    private MediaTypeRepository mediaTypeRepository;


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
//                .extension(extensionRepository.getById(model.getExtension().getId()))
//                .fileId(model.getFileId())
//                .mediaType(mediaTypeRepository.getById(model.getMediaType().getId()))
                .extension(model.getExtension())
                .fileId(model.getFileId())
                .mediaType(model.getMediaType())
                .projectId(model.getProject().getId())
                .build();
    }

    @Override
    public Media convertToModel(MediaDto dto) {
        return Media.builder()
                .id(dto.getId())
                .name(dto.getName())
//                .extension(extensionRepository.getById(dto.getExtension().getId()))
//                .fileId(dto.getFileId())
//                .mediaType(mediaTypeRepository.getById(dto.getMediaType().getId()))
                .extension(dto.getExtension())
                .fileId(dto.getFileId())
                .mediaType(dto.getMediaType())
                .project(projectRepository.getById(dto.getProjectId()))
                .build();
    }
}
