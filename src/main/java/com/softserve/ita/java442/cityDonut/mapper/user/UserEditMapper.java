package com.softserve.ita.java442.cityDonut.mapper.user;

import com.softserve.ita.java442.cityDonut.dto.user.UserEditDto;
import com.softserve.ita.java442.cityDonut.model.User;

public class UserEditMapper {

    public static UserEditDto convertToDto(User user){
        return UserEditDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();
    }

    public static User convertToEntity(UserEditDto userDtoEdit){
        return User.builder()
                .id(userDtoEdit.getId())
                .email(userDtoEdit.getEmail())
                .firstName(userDtoEdit.getFirstName())
                .lastName(userDtoEdit.getLastName())
                .build();
    }
}
