package com.softserve.ita.java442.cityDonut.dto.storyBoard;

import com.softserve.ita.java442.cityDonut.dto.gallery.GalleryDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoryBoardDto {

    private long id;

    private long projectId;

    private String description;

    private GalleryDto galleryDto;

    private Timestamp date;

    private boolean isVerified;

    private long moneySpent;
}
