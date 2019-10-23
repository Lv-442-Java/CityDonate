package com.softserve.ita.java442.cityDonut.dto.comment;

import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommentShowDto {

    private long id;

    private String message;

    private long userId;

    private String userName;

    private long projectId;

    private LocalDateTime date;
}
