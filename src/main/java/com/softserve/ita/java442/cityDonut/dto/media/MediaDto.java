package com.softserve.ita.java442.cityDonut.dto.media;

import com.softserve.ita.java442.cityDonut.model.Extension;
import com.softserve.ita.java442.cityDonut.model.MediaType;
import lombok.*;

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

    private LocalDateTime creationDate;

    private long projectId;
}
