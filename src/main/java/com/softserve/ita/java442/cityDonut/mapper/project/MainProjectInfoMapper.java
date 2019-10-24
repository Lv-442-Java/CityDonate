package com.softserve.ita.java442.cityDonut.mapper.project;

import com.softserve.ita.java442.cityDonut.dto.project.MainProjectInfoDto;
import com.softserve.ita.java442.cityDonut.mapper.GeneralMapper;
import com.softserve.ita.java442.cityDonut.model.Project;

public class MainProjectInfoMapper implements GeneralMapper<Project, MainProjectInfoDto> {

    @Override
    public MainProjectInfoDto convertToDto(Project model) {
        return MainProjectInfoDto.builder()
                .id(model.getId())
                .creationDate(model.getCreationDate())
                .description(model.getDescription())
                .donationEndDate(model.getDonationEndDate())
                .location(model.getLocation())
                .locationLatitude(model.getLocationLatitude())
                .locationLongitude(model.getLocationLongitude())
                .name(model.getName())
                .publicationDate(model.getPublicationDate())
                .realizationEndDate(model.getRealizationEndDate())
                .owner(model.getOwner())
                .moneyNeeded(model.getMoneyNeeded())
                .categories(model.getCategories())
                .comments(model.getComments())
                .donates(model.getDonates())
                .media(model.getMedia())
                .projectStatus(model.getProjectStatus())
                .storyBoards(model.getStoryBoards())
                .build();
    }

    @Override
    public Project convertToModel(MainProjectInfoDto dto) {
        return Project.builder()
                .id(dto.getId())
                .creationDate(dto.getCreationDate())
                .description(dto.getDescription())
                .donationEndDate(dto.getDonationEndDate())
                .location(dto.getLocation())
                .locationLatitude(dto.getLocationLatitude())
                .locationLongitude(dto.getLocationLongitude())
                .name(dto.getName())
                .publicationDate(dto.getPublicationDate())
                .realizationEndDate(dto.getRealizationEndDate())
                .owner(dto.getOwner())
                .moneyNeeded(dto.getMoneyNeeded())
                .categories(dto.getCategories())
                .comments(dto.getComments())
                .donates(dto.getDonates())
                .media(dto.getMedia())
                .projectStatus(dto.getProjectStatus())
                .storyBoards(dto.getStoryBoards())
                .build();
    }
}
