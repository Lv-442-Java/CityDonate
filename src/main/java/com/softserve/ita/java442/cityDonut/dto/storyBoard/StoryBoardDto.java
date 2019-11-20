package com.softserve.ita.java442.cityDonut.dto.storyBoard;

import com.softserve.ita.java442.cityDonut.dto.gallery.GalleryDto;
import com.softserve.ita.java442.cityDonut.dto.media.MediaDto;
import com.softserve.ita.java442.cityDonut.model.Gallery;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
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

    private GalleryDto galleryDto;

    private Timestamp date;

    private boolean isVerified;

    private long moneySpent;
}
