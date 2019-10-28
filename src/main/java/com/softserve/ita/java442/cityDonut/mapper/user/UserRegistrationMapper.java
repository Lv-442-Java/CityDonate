package com.softserve.ita.java442.cityDonut.mapper.user;

import com.softserve.ita.java442.cityDonut.dto.user.UserRegistrationDto;
import com.softserve.ita.java442.cityDonut.mapper.GeneralMapper;
import com.softserve.ita.java442.cityDonut.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserRegistrationMapper implements GeneralMapper<User, UserRegistrationDto> {

    @Override
    public UserRegistrationDto convertToDto(User model) {
        return UserRegistrationDto.builder()
                .firstName(model.getFirstName())
                .lastName(model.getLastName())
                .email(model.getEmail())
                .build();
    }

    @Override
    public User convertToModel(UserRegistrationDto dto) {
        return User.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .build();
    }
}
