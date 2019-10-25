package com.softserve.ita.java442.cityDonut.dto;

import com.softserve.ita.java442.cityDonut.model.ProjectStatus;
import com.softserve.ita.java442.cityDonut.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDto {

    private long id;

    private String name;

    private String description;

    private String location;

    private String locationLatitude;

    private String locationLongitude;

    private LocalDateTime creationDate;

    private LocalDateTime publicationDate;

    private LocalDateTime donationEndDate;

    private LocalDateTime realizationEndDate;

    private ProjectStatusDto projectStatusDto;

    //private UserDto ownerDto;
}
