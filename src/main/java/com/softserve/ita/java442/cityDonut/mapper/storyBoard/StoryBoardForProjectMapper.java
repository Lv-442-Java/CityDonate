package com.softserve.ita.java442.cityDonut.mapper.storyBoard;

import com.softserve.ita.java442.cityDonut.dto.storyBoard.StoryBoardForProjectDto;
import com.softserve.ita.java442.cityDonut.mapper.GeneralMapper;
import com.softserve.ita.java442.cityDonut.mapper.media.MediaMapper;
import com.softserve.ita.java442.cityDonut.model.StoryBoard;
import com.softserve.ita.java442.cityDonut.repository.GalleryRepository;
import com.softserve.ita.java442.cityDonut.repository.ProjectRepository;
import com.softserve.ita.java442.cityDonut.repository.StoryBoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class StoryBoardForProjectMapper implements GeneralMapper<StoryBoard, StoryBoardForProjectDto> {

    @Autowired
    StoryBoardRepository storyBoardRepository;

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    GalleryRepository galleryRepository;

    @Autowired
    MediaMapper mediaMapper;

    public List<StoryBoardForProjectDto> convertListToDto(List<StoryBoard> modelList) {
        List<StoryBoardForProjectDto> dtoList = new ArrayList<>();
        for (StoryBoard model : modelList) {
            dtoList.add(convertToDto(model));
        }
        return dtoList;
    }

    public List<StoryBoard> convertListToModel(List<StoryBoardForProjectDto> dtoList) {
        List<StoryBoard> modelList = new ArrayList<>();
        for (StoryBoardForProjectDto dto : dtoList) {
            modelList.add(convertToModel(dto));
        }
        return modelList;
    }

    @Override
    public StoryBoardForProjectDto convertToDto(StoryBoard model) {
        return StoryBoardForProjectDto.builder()
                .id(model.getId())
                .date(model.getDate())
                .description(model.getDescription())
              //  .media(mediaMapper.convertListToDto(model.getMedia()))
                .galleryId(storyBoardRepository.getOne(model.getId()).getGallery().getId())
                .build();
    }

    @Override
    public StoryBoard convertToModel(StoryBoardForProjectDto dto) {
        return StoryBoard.builder()
                .id(dto.getId())
                .date(dto.getDate())
                .description(dto.getDescription())
                .gallery(storyBoardRepository.getOne(dto.getId()).getGallery())
                .build();
    }
}
