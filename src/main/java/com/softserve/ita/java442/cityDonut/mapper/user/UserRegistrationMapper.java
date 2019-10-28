package com.softserve.ita.java442.cityDonut.mapper.user;

import com.softserve.ita.java442.cityDonut.dto.user.UserRegistrationDto;
import com.softserve.ita.java442.cityDonut.mapper.GeneralMapper;
import com.softserve.ita.java442.cityDonut.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserRegistrationMapper implements GeneralMapper<User,UserForRegistrationMapper> {

    @Override
    public UserForRegistrationMapper convertToDto(User model) {
        return UserRegistrationDto.builder()
                .id(model.getId())
                .firstName(model.getFirstName())
                .lastName(model.getLastName())
                .build();    }

    @Override
    public User convertToModel(UserForRegistrationMapper dto) {
        return  User.builder()
                .id(dto.getId())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .build();
    }
}
