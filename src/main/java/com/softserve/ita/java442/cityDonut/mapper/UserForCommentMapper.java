package com.softserve.ita.java442.cityDonut.mapper;

import com.softserve.ita.java442.cityDonut.dto.user.UserForCommentDto;
import com.softserve.ita.java442.cityDonut.model.User;

public class UserForCommentMapper {

    public static User convertToModel(UserForCommentDto dto){
        return User.builder().id(dto.getUserId())
            .firstName(dto.getUserName())
                .build();

    }

    public static UserForCommentDto convertToDto(User user){
        return UserForCommentDto.builder()
                .userId(user.getId())
                .userName(user.getFirstName())
                .build();

    }


}
