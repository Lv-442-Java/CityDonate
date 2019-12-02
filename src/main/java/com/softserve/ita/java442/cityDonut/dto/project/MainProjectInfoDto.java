package com.softserve.ita.java442.cityDonut.dto.project;

import com.softserve.ita.java442.cityDonut.dto.category.CategoryDto;
import com.softserve.ita.java442.cityDonut.dto.projectStatus.ProjectStatusDto;
import com.softserve.ita.java442.cityDonut.dto.user.UserNameDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MainProjectInfoDto {

    private long id;

    private String name;

    private String description;

    private String location;

    private double locationLatitude;

    private double locationLongitude;

    private Timestamp publicationDate;

    private Timestamp donationEndDate;

    private Timestamp realizationEndDate;

    private ProjectStatusDto projectStatus;

    private UserNameDto owner;

    private List<CategoryDto> categories;

    private long moneyNeeded;

    private long moneyDonated;
}
