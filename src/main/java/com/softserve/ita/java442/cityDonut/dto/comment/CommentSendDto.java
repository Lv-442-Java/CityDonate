package com.softserve.ita.java442.cityDonut.dto.comment;

import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommentSendDto {

    private long id;

    private String message;

    private long userId;

    private long projectId;

    private LocalDateTime date;
}
