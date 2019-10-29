package com.softserve.ita.java442.cityDonut.dto.authentication;

import lombok.Data;

@Data
public class AuthenticationRequestDto {
    private String userEmail;
    private String password;
}
