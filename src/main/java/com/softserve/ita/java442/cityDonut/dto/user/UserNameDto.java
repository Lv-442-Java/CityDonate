package com.softserve.ita.java442.cityDonut.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserNameDto {
    private long id;
    private String firstName;
    private String lastName;
}
