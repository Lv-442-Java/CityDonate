package com.softserve.ita.java442.cityDonut.dto.project;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectInfoDto {

    private long id;

    private String name;

    private String ownerFirstName;

    private String ownerLastName;

    private LocalDateTime creationDate;
}
