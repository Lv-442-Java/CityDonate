package com.softserve.ita.java442.cityDonut.mapper.storyBoard;

import com.softserve.ita.java442.cityDonut.dto.storyBoard.StoryBoardDto;
import com.softserve.ita.java442.cityDonut.mapper.GeneralMapper;
import com.softserve.ita.java442.cityDonut.mapper.media.MediaMapper;
import com.softserve.ita.java442.cityDonut.model.StoryBoard;
import com.softserve.ita.java442.cityDonut.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class StoryBoardMapper implements GeneralMapper<StoryBoard, StoryBoardDto> {

    @Autowired
    MediaMapper mediaMapper;
    @Autowired
    ProjectRepository projectRepository;

    @Override
    public StoryBoardDto convertToDto(StoryBoard model) {
        return StoryBoardDto.builder()
                .id(model.getId())
                .projectId(model.getProject().getId())
                .date(model.getDate())
                .description(model.getDescription())
                .media(mediaMapper.convertListToDto(model.getMedia()))
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
                .media(mediaMapper.convertListToModel(dto.getMedia()))
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
