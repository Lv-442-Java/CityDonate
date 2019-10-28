package com.softserve.ita.java442.cityDonut.dto.project;

import com.softserve.ita.java442.cityDonut.dto.UserDto;
import com.softserve.ita.java442.cityDonut.dto.category.CategoryNameDto;
import com.softserve.ita.java442.cityDonut.dto.status.ProjectStatusDTO;
import com.softserve.ita.java442.cityDonut.dto.user.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewProjectDTO {

    private long id;

    @NotNull
    @Size(
            min = 4,
            max = 50,
            message = "hi!"
    )
    private String name;

    @Size(max = 1000)
    private String description;

    @Size(max = 255)
    private String location;

    private String locationLatitude;

    private String locationLongitude;

    private List<CategoryNameDto> categories;

}
