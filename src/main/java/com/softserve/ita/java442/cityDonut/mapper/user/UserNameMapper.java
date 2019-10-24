package com.softserve.ita.java442.cityDonut.mapper.user;

import com.softserve.ita.java442.cityDonut.dto.user.UserNameDto;
import com.softserve.ita.java442.cityDonut.mapper.GeneralMapper;
import com.softserve.ita.java442.cityDonut.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserNameMapper implements GeneralMapper<User, UserNameDto> {

    @Override
    public UserNameDto convertToDto(User model) {
        return UserNameDto.builder()
                .id(model.getId())
                .firstName(model.getFirstName())
                .lastName(model.getLastName())
                .build();
    }

    @Override
    public User convertToModel(UserNameDto dto) {
        return User.builder()
                .id(dto.getId())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .build();
    }
}
