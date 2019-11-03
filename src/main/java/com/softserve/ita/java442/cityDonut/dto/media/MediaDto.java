package com.softserve.ita.java442.cityDonut.dto.media;

import com.softserve.ita.java442.cityDonut.mapper.media.MediaMapper;
import com.softserve.ita.java442.cityDonut.model.Media;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Column;
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

    @Autowired
    private MediaMapper mediaMapper;

}
