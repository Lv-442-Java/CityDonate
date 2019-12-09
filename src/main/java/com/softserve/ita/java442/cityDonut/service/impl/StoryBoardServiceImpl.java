package com.softserve.ita.java442.cityDonut.service.impl;

import com.softserve.ita.java442.cityDonut.constant.ErrorMessage;
import com.softserve.ita.java442.cityDonut.dto.storyBoard.StoryBoardDto;
import com.softserve.ita.java442.cityDonut.exception.NotFoundException;
import com.softserve.ita.java442.cityDonut.mapper.gallery.GalleryMapper;
import com.softserve.ita.java442.cityDonut.mapper.storyBoard.StoryBoardMapper;
import com.softserve.ita.java442.cityDonut.model.Gallery;
import com.softserve.ita.java442.cityDonut.model.StoryBoard;
import com.softserve.ita.java442.cityDonut.repository.GalleryRepository;
import com.softserve.ita.java442.cityDonut.repository.ProjectRepository;
import com.softserve.ita.java442.cityDonut.repository.StoryBoardRepository;
import com.softserve.ita.java442.cityDonut.service.StoryBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class StoryBoardServiceImpl implements StoryBoardService {

    private StoryBoardRepository storyBoardRepository;
    private ProjectRepository projectRepository;
    private StoryBoardMapper mapper;
    private GalleryMapper galleryMapper;
    private GalleryRepository galleryRepository;

    @Autowired
    public StoryBoardServiceImpl(StoryBoardRepository storyBoardRepository, ProjectRepository projectRepository,
                                 StoryBoardMapper mapper, GalleryMapper galleryMapper,
                                 GalleryRepository galleryRepository) {
        this.storyBoardRepository = storyBoardRepository;
        this.projectRepository = projectRepository;
        this.mapper = mapper;
        this.galleryMapper = galleryMapper;
        this.galleryRepository = galleryRepository;
    }


    @Override
    public List<StoryBoardDto> getStoryBoardsByProject(long projectId) {
        List<StoryBoardDto> storyBoardDtos;
        storyBoardDtos = mapper.convertListToDto(storyBoardRepository.getStoryBoardsByProject_Id(projectId));
        return storyBoardDtos;
    }

    @Override
    public List<StoryBoardDto> getVerifiedStoryBoardsByProject(long projectId) {
        List<StoryBoardDto> storyBoardDtos;
        storyBoardDtos = mapper.convertListToDto(storyBoardRepository.getStoryBoardsByProject_IdAndIsVerifiedIsTrueOrderByDateDesc(projectId));
        return storyBoardDtos;
    }

    @Override
    public StoryBoardDto createStoryBoard(StoryBoardDto storyBoard, long projectId) {
        StoryBoard storyBoardModel = new StoryBoard();
        storyBoardModel.setProject(projectRepository.getById(projectId));
        storyBoardModel.setDescription(storyBoard.getDescription());
        storyBoardModel.setMoneySpent(storyBoard.getMoneySpent());
        storyBoardModel.setVerified(false);
        storyBoardModel.setDate(new Timestamp(System.currentTimeMillis()));
        StoryBoard resultOfQuery = storyBoardRepository.save(storyBoardModel);
        Gallery gallery = new Gallery();
        gallery.setStoryBoard(resultOfQuery);
        resultOfQuery.setGallery(galleryRepository.saveAndFlush(gallery));
        StoryBoardDto result = mapper.convertToDto(resultOfQuery);
        storyBoardRepository.flush();
        return result;
    }

    @Override
    public StoryBoardDto editStoryBoard(StoryBoardDto storyBoardDto) {

        StoryBoard model;
        if (storyBoardDto != null) {
            model = storyBoardRepository
                    .findById(storyBoardDto.getId())
                    .orElseThrow(() -> new NotFoundException(ErrorMessage.STORYBOARD_NOT_FOUND_BY_ID + storyBoardDto.getId()));
        } else {
            throw new NotFoundException(ErrorMessage.STORYBOARD_NOT_FOUND_BY_ID);
        }
        model.setId(storyBoardDto.getId());
        model.setDate(storyBoardDto.getDate());
        model.setDescription(storyBoardDto.getDescription());
        model.setMoneySpent(storyBoardDto.getMoneySpent());
        model.setVerified(storyBoardDto.isVerified());
        model.setProject(projectRepository.getById(storyBoardDto.getProjectId()));
        model.setGallery(galleryMapper.convertToModel(storyBoardDto.getGalleryDto()));

        return mapper.convertToDto(storyBoardRepository.save(model));
    }

    @Override
    public Boolean deleteStoryBoard(long storyBoardId) {
        storyBoardRepository.delete(storyBoardRepository.getOne(storyBoardId));
        return true;
    }
}
