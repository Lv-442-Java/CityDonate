package com.softserve.ita.java442.cityDonut.dto.media;

import com.softserve.ita.java442.cityDonut.model.Extension;
import com.softserve.ita.java442.cityDonut.model.MediaType;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"extension", "mediaType"})
public class MediaDto {
    private long id;

    private String name;

    private Extension extension;

    private String fileId;

    private MediaType mediaType;

    private Timestamp creationDate;

    private long projectId;

    private long storyBoardId;
}
