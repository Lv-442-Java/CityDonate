package com.softserve.ita.java442.cityDonut.dto.comment;

import com.softserve.ita.java442.cityDonut.dto.ProjectDto;
import com.softserve.ita.java442.cityDonut.dto.user.UserForCommentDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    private long id;

    private String description;

    private LocalDateTime date;

    private long projectId;

     private UserForCommentDto userDto;
}
