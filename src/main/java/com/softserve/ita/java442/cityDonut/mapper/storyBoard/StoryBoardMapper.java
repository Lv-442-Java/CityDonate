package com.softserve.ita.java442.cityDonut.mapper.storyBoard;

import com.softserve.ita.java442.cityDonut.dto.storyBoard.StoryBoardDto;
import com.softserve.ita.java442.cityDonut.mapper.GeneralMapper;
import com.softserve.ita.java442.cityDonut.mapper.gallery.GalleryMapper;
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
public class StoryBoardMapper implements GeneralMapper<StoryBoard, StoryBoardDto> {

    @Autowired
    StoryBoardRepository storyBoardRepository;
    @Autowired
    MediaMapper mediaMapper;
    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    GalleryMapper galleryMapper;

    @Override
    public StoryBoardDto convertToDto(StoryBoard model) {
        return StoryBoardDto.builder()
                .id(model.getId())
                .projectId(model.getProject().getId())
                .date(model.getDate())
                .description(model.getDescription())
                .galleryDto(galleryMapper.convertToDto(storyBoardRepository.getOne(model.getId()).getGallery()))
                .isVerified(model.isVerified())
                .moneySpent(model.getMoneySpent())
                .build();
    }

    @Override
    public StoryBoard convertToModel(StoryBoardDto dto) {
        return StoryBoard.builder()
                .id(dto.getId())
                .project(projectRepository.getById(dto.getProjectId()))
                .date(dto.getDate())
                .description(dto.getDescription())
                .gallery(storyBoardRepository.getOne(dto.getId()).getGallery())
                .moneySpent(dto.getMoneySpent())
                .isVerified(dto.isVerified())
                .build();
    }

    public List<StoryBoardDto> convertListToDto(List<StoryBoard> storyBoards) {
        List<StoryBoardDto> dtoList = new ArrayList<>();
        for (StoryBoard storyBoard : storyBoards) {
            dtoList.add(convertToDto(storyBoard));
        }
        return dtoList;
    }
}
