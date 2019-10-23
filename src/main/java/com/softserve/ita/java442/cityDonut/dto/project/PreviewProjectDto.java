package com.softserve.ita.java442.cityDonut.dto.project;

import com.softserve.ita.java442.cityDonut.dto.category.CategoryDto;
import com.softserve.ita.java442.cityDonut.dto.projectStatus.ProjectStatusDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PreviewProjectDto {

    private long id;
    private String name;
    private List<CategoryDto> categories;
    private ProjectStatusDto projectStatusDto;
    private long moneyNeeded;
}
