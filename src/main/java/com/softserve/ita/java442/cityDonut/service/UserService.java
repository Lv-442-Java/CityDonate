package com.softserve.ita.java442.cityDonut.service;

import com.softserve.ita.java442.cityDonut.dto.project.ProjectInfoDto;
import com.softserve.ita.java442.cityDonut.dto.user.UserEditDto;
import com.softserve.ita.java442.cityDonut.dto.user.UserRoleDto;
import com.softserve.ita.java442.cityDonut.model.User;
import com.softserve.ita.java442.cityDonut.dto.user.UserEditPasswordDto;
import com.softserve.ita.java442.cityDonut.dto.user.UserRegistrationDto;

import java.util.List;

public interface UserService {
    User findUserByEmail(String email);

    User getCurrentUser();

    UserEditDto update(UserEditDto userEditDto);

    UserEditDto getUserEditDto();

    boolean validateUser(UserRegistrationDto userRegistrationDto);

    boolean registerUser(UserRegistrationDto userRegistrationDto);

    void changePassword(UserEditPasswordDto userEditPasswordDto);

    User activateUserByCode(String activationCode);

    UserRoleDto getUserRoleDto(long userId);

    List<UserRoleDto> getUsersRoleDto(long projectId);

    UserRoleDto getCurrentUserSubscribedToProject(long projectId);

    UserRoleDto subscribeCurrentUserToProject(long projectId);

    UserRoleDto unsubscribeCurrentUserFromProject(long projectId);
}