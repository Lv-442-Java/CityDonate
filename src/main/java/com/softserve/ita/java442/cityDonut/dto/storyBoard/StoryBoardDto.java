package com.softserve.ita.java442.cityDonut.dto.storyBoard;

import com.softserve.ita.java442.cityDonut.dto.media.MediaDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoryBoardDto {

    private long id;

    private long projectId;

    private String description;

    private LocalDateTime date;

    private List<MediaDto> media;

    private boolean isVerified;

    private long moneySpent;
}
