package com.softserve.ita.java442.cityDonut.mapper.project;

import com.softserve.ita.java442.cityDonut.dto.project.MainProjectInfoDto;
import com.softserve.ita.java442.cityDonut.mapper.GeneralMapper;
import com.softserve.ita.java442.cityDonut.mapper.category.CategoryMapper;
import com.softserve.ita.java442.cityDonut.mapper.media.MediaMapper;
import com.softserve.ita.java442.cityDonut.mapper.storyBoard.StoryBoardForProjectMapper;
import com.softserve.ita.java442.cityDonut.mapper.user.UserNameMapper;
import com.softserve.ita.java442.cityDonut.mapper.projectStatus.ProjectStatusMapper;
import com.softserve.ita.java442.cityDonut.model.Donate;
import com.softserve.ita.java442.cityDonut.model.Project;
import org.springframework.stereotype.Component;

@Component
public class MainProjectInfoMapper implements GeneralMapper<Project, MainProjectInfoDto> {

    private long countDonatedMoney(Project model){
        long donatedMoney = 0;
        for(Donate donate:model.getDonates()){
            donatedMoney+=donate.getSum();
        }
        return donatedMoney;
    }

    @Override
    public MainProjectInfoDto convertToDto(Project model) {
        ProjectStatusMapper projectStatusMapper = new ProjectStatusMapper();
        UserNameMapper userNameMapper = new UserNameMapper();
        StoryBoardForProjectMapper storyBoardForProjectMapper = new StoryBoardForProjectMapper();
        MediaMapper mediaMapper = new MediaMapper();
        CategoryMapper categoryMapper = new CategoryMapper();
        return MainProjectInfoDto.builder()
                .id(model.getId())
                .name(model.getName())
                .description(model.getDescription())
                .location(model.getLocation())
                .locationLongitude(model.getLocationLongitude())
                .locationLatitude(model.getLocationLatitude())
                .publicationDate(model.getPublicationDate())
                .donationEndDate(model.getDonationEndDate())
                .realizationEndDate(model.getRealizationEndDate())
                .projectStatus(projectStatusMapper.convertToDto(model.getProjectStatus()))
                .owner(userNameMapper.convertToDto(model.getOwner()))
                .storyBoards(storyBoardForProjectMapper.convertListToDto(model.getStoryBoards()))
                .media(mediaMapper.convertListToDto(model.getMedia()))
                .categories(categoryMapper.convertListToDto(model.getCategories()))
                .moneyDonated(countDonatedMoney(model))
                .moneyNeeded(model.getMoneyNeeded())
                .build();
    }

    @Override
    public Project convertToModel(MainProjectInfoDto dto) {
        ProjectStatusMapper projectStatusMapper = new ProjectStatusMapper();
        UserNameMapper userNameMapper = new UserNameMapper();
        StoryBoardForProjectMapper storyBoardForProjectMapper = new StoryBoardForProjectMapper();
        MediaMapper mediaMapper = new MediaMapper();
        CategoryMapper categoryMapper = new CategoryMapper();
        return Project.builder()
                .id(dto.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .location(dto.getLocation())
                .locationLongitude(dto.getLocationLongitude())
                .locationLatitude(dto.getLocationLatitude())
                .publicationDate(dto.getPublicationDate())
                .donationEndDate(dto.getDonationEndDate())
                .realizationEndDate(dto.getRealizationEndDate())
                .projectStatus(projectStatusMapper.convertToModel(dto.getProjectStatus()))
                .owner(userNameMapper.convertToModel(dto.getOwner()))
                .storyBoards(storyBoardForProjectMapper.convertListToModel(dto.getStoryBoards()))
                .media(mediaMapper.convertListToModel(dto.getMedia()))
                .categories(categoryMapper.convertListToModel(dto.getCategories()))
                .moneyNeeded(dto.getMoneyNeeded())
                .build();
    }
}
