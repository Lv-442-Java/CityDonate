package com.softserve.ita.java442.cityDonut.service;

import com.softserve.ita.java442.cityDonut.dto.storyBoard.StoryBoardDto;

import java.util.List;

public interface StoryBoardService {
    List<StoryBoardDto> getStoryBoardsByProject(long projectId);

    List<StoryBoardDto> getVerifiedStoryBoardsByProject(long projectId);

    StoryBoardDto createStoryBoard(StoryBoardDto storyBoard, long projectId);

    StoryBoardDto editStoryBoard(StoryBoardDto storyBoardDto);

    Boolean deleteStoryBoard(long storyBoardId);
}
