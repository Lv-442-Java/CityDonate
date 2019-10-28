package com.softserve.ita.java442.cityDonut.mapper.user;

import com.softserve.ita.java442.cityDonut.mapper.GeneralMapper;
import com.softserve.ita.java442.cityDonut.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserForRegistrationMapper implements GeneralMapper<User,UserForRegistrationMapper> {

    @Override
    public UserForRegistrationMapper convertToDto(User model) {
        return null;
    }

    @Override
    public User convertToModel(UserForRegistrationMapper dto) {
        return null;
    }
}
