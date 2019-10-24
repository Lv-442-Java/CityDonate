package com.softserve.ita.java442.cityDonut.service;

import com.softserve.ita.java442.cityDonut.dto.user.UserEditDto;
import com.softserve.ita.java442.cityDonut.dto.user.UserEditPasswordDto;

public interface UserService {

    UserEditDto update(UserEditDto userEditDto);

    UserEditDto findById(long id);

    void changePassword(UserEditPasswordDto userEditPasswordDto);
}
