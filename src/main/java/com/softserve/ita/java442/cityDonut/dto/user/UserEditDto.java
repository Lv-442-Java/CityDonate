package com.softserve.ita.java442.cityDonut.dto.user;

import com.softserve.ita.java442.cityDonut.constant.ErrorMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEditDto {

    private long id;

    @NotNull(message = ErrorMessage.FIELD_CANT_BE_NULL)
    private String email;

    @Size(
            min = 4,
            max = 50,
            message = ErrorMessage.INCORRECT_SIZE
    )
    @NotNull(message = ErrorMessage.FIELD_CANT_BE_NULL)
    private String firstName;

    @Size(
            min = 4,
            max = 50,
            message = ErrorMessage.INCORRECT_SIZE
    )
    @NotNull(message = ErrorMessage.FIELD_CANT_BE_NULL)
    private String lastName;
}
