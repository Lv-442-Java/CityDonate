package com.softserve.ita.java442.cityDonut.mapper.user;

import com.softserve.ita.java442.cityDonut.dto.user.UserRoleDto;
import com.softserve.ita.java442.cityDonut.mapper.GeneralMapper;
import com.softserve.ita.java442.cityDonut.model.Role;
import com.softserve.ita.java442.cityDonut.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserRoleMapper implements GeneralMapper<User, UserRoleDto> {

    @Override
    public UserRoleDto convertToDto(User model) {
        return UserRoleDto.builder()
                .id(model.getId())
                .firstName(model.getFirstName())
                .lastName(model.getLastName())
                .role(model.getRole().getRole())
                .build();
    }

    @Override
    public User convertToModel(UserRoleDto dto) {
        return User.builder()
                .id(dto.getId())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .role(Role.builder().role(dto.getRole()).build())
                .build();
    }

    public List<UserRoleDto> converListToDto(List<User> userList) {
        List<UserRoleDto> userRoleDtoList = new ArrayList<>(userList.size());
        userList.forEach((user -> {
            userRoleDtoList.add(new UserRoleDto(user.getId(), user.getFirstName(), user.getLastName(), user.getRole().getRole()));
        }));
        return userRoleDtoList;
    }

}
