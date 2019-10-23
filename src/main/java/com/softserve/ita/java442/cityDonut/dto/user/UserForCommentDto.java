package com.softserve.ita.java442.cityDonut.dto.user;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class UserForCommentDto {

    private long userId;

    private String userName;

}
