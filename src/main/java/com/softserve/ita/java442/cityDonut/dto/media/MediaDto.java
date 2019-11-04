package com.softserve.ita.java442.cityDonut.dto.media;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MediaDto {
    private long id;

    private String name;

    private String extension;

    private String fileId;

    private LocalDateTime creationDate;

    private long projectId;
}
