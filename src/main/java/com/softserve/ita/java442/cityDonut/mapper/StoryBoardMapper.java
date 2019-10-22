package com.softserve.ita.java442.cityDonut.mapper;

import com.softserve.ita.java442.cityDonut.dto.StoryBoardDto;
import com.softserve.ita.java442.cityDonut.model.StoryBoard;
import org.modelmapper.ModelMapper;

public class StoryBoardMapper {

    public static StoryBoardDto convertToDto(StoryBoard storyBoard) {
        return new ModelMapper().map(storyBoard, StoryBoardDto.class);
    }

    public static StoryBoard convertToModel(StoryBoardDto storyBoardDto) {
        return new ModelMapper().map(storyBoardDto, StoryBoard.class);
    }
}
