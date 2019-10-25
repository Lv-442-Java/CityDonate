package com.softserve.ita.java442.cityDonut.mapper.user;

import com.softserve.ita.java442.cityDonut.dto.user.UserDTO;
import com.softserve.ita.java442.cityDonut.model.User;

public class UserMapper {

    public static UserDTO convertToDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .build();
    }

    public static User convertToModel(UserDTO userDTO) {
        return User.builder()
                .id(userDTO.getId())
                .build();
    }

}
