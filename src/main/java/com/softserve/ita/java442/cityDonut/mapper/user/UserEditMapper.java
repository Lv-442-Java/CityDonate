package com.softserve.ita.java442.cityDonut.mapper.user;

import com.softserve.ita.java442.cityDonut.dto.user.UserEditDto;
import com.softserve.ita.java442.cityDonut.mapper.GeneralMapper;
import com.softserve.ita.java442.cityDonut.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserEditMapper implements GeneralMapper<User, UserEditDto> {
    @Override
    public UserEditDto convertToDto(User model) {
        return UserEditDto.builder()
                .id(model.getId())
                .email(model.getEmail())
                .firstName(model.getFirstName())
                .lastName(model.getLastName())
                .build();
    }

    @Override
    public User convertToModel(UserEditDto dto) {
        return User.builder()
                .id(dto.getId())
                .email(dto.getEmail())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .build();
    }
}
