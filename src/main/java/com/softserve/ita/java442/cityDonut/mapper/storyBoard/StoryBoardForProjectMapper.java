package com.softserve.ita.java442.cityDonut.mapper.storyBoard;

import com.softserve.ita.java442.cityDonut.dto.storyBoard.StoryBoardForProjectDto;
import com.softserve.ita.java442.cityDonut.mapper.GeneralMapper;
import com.softserve.ita.java442.cityDonut.mapper.media.MediaMapper;
import com.softserve.ita.java442.cityDonut.model.StoryBoard;

import java.util.ArrayList;
import java.util.List;

public class StoryBoardForProjectMapper implements GeneralMapper<StoryBoard, StoryBoardForProjectDto> {

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
        MediaMapper mediaMapper = new MediaMapper();
        return StoryBoardForProjectDto.builder()
                .id(model.getId())
                .date(model.getDate())
                .description(model.getDescription())
                .media(mediaMapper.convertListToDto(model.getMedia()))
                .build();
    }

    @Override
    public StoryBoard convertToModel(StoryBoardForProjectDto dto) {
        MediaMapper mediaMapper = new MediaMapper();
        return StoryBoard.builder()
                .id(dto.getId())
                .date(dto.getDate())
                .description(dto.getDescription())
                .media(mediaMapper.convertListToModel(dto.getMedia()))
                .build();
    }
}
