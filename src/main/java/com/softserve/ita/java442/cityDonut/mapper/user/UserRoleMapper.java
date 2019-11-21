package com.softserve.ita.java442.cityDonut.mapper.user;

import com.softserve.ita.java442.cityDonut.dto.user.UserRoleDto;
import com.softserve.ita.java442.cityDonut.model.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserRoleMapper {

    public List<UserRoleDto> converListToDto(List<User> userList) {
        List<UserRoleDto> userRoleDtoList = new ArrayList<>(userList.size());
        userList.forEach((user -> {
            userRoleDtoList.add(new UserRoleDto(user.getId(), user.getFirstName(), user.getLastName(), user.getRole().getRole()));
        }));
        return userRoleDtoList;
    }

}
