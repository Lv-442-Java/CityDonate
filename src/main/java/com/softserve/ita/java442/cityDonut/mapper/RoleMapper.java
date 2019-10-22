package com.softserve.ita.java442.cityDonut.mapper;

import com.softserve.ita.java442.cityDonut.dto.RoleDto;
import com.softserve.ita.java442.cityDonut.model.Role;
import org.modelmapper.ModelMapper;

public class RoleMapper {

    public static RoleDto convertToDto(Role role) {
        return new ModelMapper().map(role, RoleDto.class);
    }

    public static Role convertToModel(RoleDto roleDto) {
        return new ModelMapper().map(roleDto, Role.class);
    }
}
