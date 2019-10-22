package com.softserve.ita.java442.cityDonut.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class NewProjectDTO {

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

        private ProjectStatusDto projectStatusDto;

        private UserDto ownerDto;
    }

}
