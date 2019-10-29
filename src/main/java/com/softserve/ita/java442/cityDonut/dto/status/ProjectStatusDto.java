package com.softserve.ita.java442.cityDonut.dto.status;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProjectStatusDto {

    private long id;

    private String status;
}
