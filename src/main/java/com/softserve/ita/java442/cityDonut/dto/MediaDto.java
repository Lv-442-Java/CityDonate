package com.softserve.ita.java442.cityDonut.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MediaDto {

    private long id;

    private String name;

    private MediaTypeDto mediaType;

    private ProjectDto project;

    private StoryBoardDto storyBoard;

}
