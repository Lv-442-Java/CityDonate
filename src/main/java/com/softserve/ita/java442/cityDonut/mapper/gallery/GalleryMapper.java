package com.softserve.ita.java442.cityDonut.mapper.gallery;

import com.softserve.ita.java442.cityDonut.dto.gallery.GalleryDto;
import com.softserve.ita.java442.cityDonut.mapper.GeneralMapper;
import com.softserve.ita.java442.cityDonut.model.Gallery;
import com.softserve.ita.java442.cityDonut.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GalleryMapper implements GeneralMapper <Gallery, GalleryDto> {
@Autowired
    ProjectRepository projectRepository;
    @Override
    public GalleryDto convertToDto(Gallery model) {
        return GalleryDto.builder()
                .id(model.getId())
                //.projectId(projectRepository.getById().getId())
                .build();
    }

    @Override
    public Gallery convertToModel(GalleryDto dto) {
        return Gallery.builder()
                .id(dto.getId())
                .project(projectRepository.getById(dto.getProjectId()))
                .build();
    }
}
