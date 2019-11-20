package com.softserve.ita.java442.cityDonut.dto.gallery;

import com.softserve.ita.java442.cityDonut.model.Extension;
import com.softserve.ita.java442.cityDonut.model.MediaType;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GalleryDto {
    private long id;

    private long projectId;
}
