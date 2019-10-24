package com.softserve.ita.java442.cityDonut.dto.project;

import com.softserve.ita.java442.cityDonut.dto.category.CategoryDto;
import com.softserve.ita.java442.cityDonut.dto.media.MediaDto;
import com.softserve.ita.java442.cityDonut.dto.projectStatus.ProjectStatusDto;
import com.softserve.ita.java442.cityDonut.dto.storyBoard.StoryBoardForProjectDto;
import com.softserve.ita.java442.cityDonut.dto.user.UserNameDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
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

    private String locationLatitude;

    private String locationLongitude;

    private LocalDateTime publicationDate;

    private LocalDateTime donationEndDate;

    private LocalDateTime realizationEndDate;

    private ProjectStatusDto projectStatus;

    private UserNameDto owner;

    private List<StoryBoardForProjectDto> storyBoards;

    private List<MediaDto> media;

    private List<CategoryDto> categories;

    private long moneyNeeded;

    private long moneyDonated;
}
