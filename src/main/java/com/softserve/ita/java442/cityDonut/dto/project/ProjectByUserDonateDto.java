package com.softserve.ita.java442.cityDonut.dto.project;

import com.softserve.ita.java442.cityDonut.dto.category.CategoryDto;
import com.softserve.ita.java442.cityDonut.dto.projectStatus.ProjectStatusDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectByUserDonateDto {

    private long id;

    private String name;

    private ProjectStatusDto projectStatusDto;

    private long galleryId;

    private List<CategoryDto> categories;

    private long moneyNeeded;

    private long donateCount;

    private double donateSum;

}
