package com.softserve.ita.java442.cityDonut.dto.projectStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectStatusDto {

    private long id;
    private String status;
}