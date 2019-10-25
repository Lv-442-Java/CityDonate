package com.softserve.ita.java442.cityDonut.mapper;

import com.softserve.ita.java442.cityDonut.dto.user.UserForCommentDto;
import com.softserve.ita.java442.cityDonut.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserForCommentMapper implements GeneralMapper<User, UserForCommentDto> {

    public  User convertToModel(UserForCommentDto dto){
        return User.builder().id(dto.getUserId())
            .firstName(dto.getUserName())
                .build();
    }

    public UserForCommentDto convertToDto(User user){
        return UserForCommentDto.builder()
                .userId(user.getId())
                .userName(user.getFirstName())
                .build();
    }
}
