package com.softserve.ita.java442.cityDonut.dto.project;

import com.softserve.ita.java442.cityDonut.model.ProjectStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectByUserDonateDto {

    private long id;

    private String name;

    private long galleryId;

    private ProjectStatus projectStatus;

    private long donateCount;

    private double donateSum;
}
