package com.softserve.ita.java442.cityDonut.dto.project;

import com.softserve.ita.java442.cityDonut.dto.ProjectStatusDto;
import com.softserve.ita.java442.cityDonut.dto.UserDto;
import com.softserve.ita.java442.cityDonut.dto.status.ProjectStatusDTO;
import com.softserve.ita.java442.cityDonut.dto.user.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewProjectDTO {

    private long id;

    private String name;

    private String description;

    private String location;

    private String locationLatitude;

    private String locationLongitude;

    private LocalDateTime creationDate;

    private ProjectStatusDTO projectStatusDTO;

    private UserDTO ownerDTO;

}
