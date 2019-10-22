package com.softserve.ita.java442.cityDonut.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoryBoardDto {

    private long id;

    private String description;

    private LocalDateTime date;

    private ProjectDto projectDto;
}
