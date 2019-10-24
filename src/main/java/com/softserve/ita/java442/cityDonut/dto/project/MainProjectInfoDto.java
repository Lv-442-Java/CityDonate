package com.softserve.ita.java442.cityDonut.dto.project;

import com.softserve.ita.java442.cityDonut.model.*;
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
    private LocalDateTime creationDate;
    private LocalDateTime publicationDate;
    private LocalDateTime donationEndDate;
    private LocalDateTime realizationEndDate;
    private ProjectStatus projectStatus;
    private User owner;
    private List<Comment> comments;
    private List<Donate> donates;
    private List<StoryBoard> storyBoards;
    private List<Media> media;
    private List<Category> categories;
    private long moneyNeeded;
}
