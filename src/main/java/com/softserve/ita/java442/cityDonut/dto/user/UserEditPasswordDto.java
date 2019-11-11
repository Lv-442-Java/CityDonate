package com.softserve.ita.java442.cityDonut.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEditPasswordDto {

    private String password;

    private String newPassword;
}
