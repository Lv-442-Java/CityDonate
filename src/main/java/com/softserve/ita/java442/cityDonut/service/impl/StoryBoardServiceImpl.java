package com.softserve.ita.java442.cityDonut.service.impl;

import com.softserve.ita.java442.cityDonut.constant.ErrorMessage;
import com.softserve.ita.java442.cityDonut.dto.storyBoard.StoryBoardDto;
import com.softserve.ita.java442.cityDonut.exception.NotFoundException;
import com.softserve.ita.java442.cityDonut.mapper.media.MediaMapper;
import com.softserve.ita.java442.cityDonut.mapper.storyBoard.StoryBoardMapper;
import com.softserve.ita.java442.cityDonut.model.StoryBoard;
import com.softserve.ita.java442.cityDonut.repository.ProjectRepository;
import com.softserve.ita.java442.cityDonut.repository.StoryBoardRepository;
import com.softserve.ita.java442.cityDonut.service.StoryBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoryBoardServiceImpl implements StoryBoardService {

    @Autowired
    private StoryBoardRepository storyBoardRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private StoryBoardMapper mapper;
    @Autowired
    private MediaMapper mediaMapper;


    public List<StoryBoardDto> getStoryBoardsByProject(long projectId){
        List<StoryBoardDto> storyBoardDtos;
        storyBoardDtos =  mapper.convertListToDto(storyBoardRepository.getStoryBoardsByProject_Id(projectId));
        return storyBoardDtos;
    }

    public StoryBoardDto createStoryBoard(StoryBoardDto storyBoard){
        StoryBoardDto newStoryBoard;
        newStoryBoard = mapper.convertToDto(storyBoardRepository.save(mapper.convertToModel(storyBoard)));
        return  newStoryBoard;
    }

    public StoryBoardDto editStoryBoard(StoryBoardDto storyBoardDto){

        StoryBoard model;
        if (storyBoardDto != null) {
            model = storyBoardRepository
                    .findById(storyBoardDto.getId())
                    .orElseThrow(() -> new NotFoundException(ErrorMessage.STORYBOARD_NOT_FOUND_BY_ID + storyBoardDto.getId()));
        } else {
            throw new NotFoundException(ErrorMessage.STORYBOARD_NOT_FOUND_BY_ID);
        }
        model.setDate(storyBoardDto.getDate());
        model.setDescription(storyBoardDto.getDescription());
        model.setMedia(mediaMapper.convertListToModel(storyBoardDto.getMedia()));
        model.setMoneySpent(storyBoardDto.getMoneySpent());
        model.setVerified(storyBoardDto.isVerified());
        model.setProject(projectRepository.getById(storyBoardDto.getProjectId()));

        return mapper.convertToDto(storyBoardRepository.save(model));
    }
}
