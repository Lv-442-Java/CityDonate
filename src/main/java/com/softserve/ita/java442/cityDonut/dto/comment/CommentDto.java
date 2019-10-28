package com.softserve.ita.java442.cityDonut.dto.comment;

import lombok.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class CommentDto {

    private long id;

    private String description;

    private LocalDateTime date;

    private long projectId;

    private long userId;
}
