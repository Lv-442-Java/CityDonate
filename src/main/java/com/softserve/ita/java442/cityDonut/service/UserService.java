package com.softserve.ita.java442.cityDonut.service;

import com.softserve.ita.java442.cityDonut.dto.user.UserEditDto;
import com.softserve.ita.java442.cityDonut.dto.user.UserEditPasswordDto;
import com.softserve.ita.java442.cityDonut.dto.user.UserRegistrationDto;
import com.softserve.ita.java442.cityDonut.model.User;

public interface UserService {

    UserEditDto update(UserEditDto userEditDto);

    UserEditDto findById(long id);

    UserRegistrationDto saveUser(UserRegistrationDto userRegistrationDto);

    void changePassword(UserEditPasswordDto userEditPasswordDto);

}
