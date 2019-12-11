package com.softserve.ita.java442.cityDonut.service.impl;

import com.softserve.ita.java442.cityDonut.constant.ErrorMessage;
import com.softserve.ita.java442.cityDonut.dto.lastChatUpdated.LastChatUpdatedDto;
import com.softserve.ita.java442.cityDonut.exception.ProjectNotFoundException;
import com.softserve.ita.java442.cityDonut.mapper.lastChatUpdated.LastChatUpdatedMapper;
import com.softserve.ita.java442.cityDonut.model.LastChatUpdated;
import com.softserve.ita.java442.cityDonut.model.Project;
import com.softserve.ita.java442.cityDonut.model.User;
import com.softserve.ita.java442.cityDonut.repository.LastChatUpdatedRepository;
import com.softserve.ita.java442.cityDonut.repository.ProjectRepository;
import com.softserve.ita.java442.cityDonut.service.LastChatUpdatedService;
import com.softserve.ita.java442.cityDonut.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class LastChatUpdatedServiceImpl implements LastChatUpdatedService {

    private final LastChatUpdatedRepository lastChatUpdatedRepository;

    private final UserService userService;

    private final ProjectRepository projectRepository;

    private final LastChatUpdatedMapper lastChatUpdatedMapper;

    @Autowired
    public LastChatUpdatedServiceImpl(LastChatUpdatedRepository lastChatUpdatedRepository, UserService userService, ProjectRepository projectRepository, LastChatUpdatedMapper lastChatUpdatedMapper) {
        this.lastChatUpdatedRepository = lastChatUpdatedRepository;
        this.userService = userService;
        this.projectRepository = projectRepository;
        this.lastChatUpdatedMapper = lastChatUpdatedMapper;
    }

    @Override
    public LastChatUpdatedDto setNewUpdatedTime(long projectId) {
        User user = userService.getCurrentUser();
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException(ErrorMessage.PROJECT_NOT_FOUND_BY_ID));
        LastChatUpdated lastChatUpdated = lastChatUpdatedRepository.findByUserAndProject(user, project)
                .orElse(
                        LastChatUpdated.builder()
                                .user(user)
                                .project(project)
                                .build()
                );
        lastChatUpdated.setDateTime(Timestamp.valueOf(LocalDateTime.now()));
        return lastChatUpdatedMapper.convertToDto(lastChatUpdatedRepository.save(lastChatUpdated));
    }

    @Override
    public List<LastChatUpdatedDto> getChatUpdatedTimes(long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException(ErrorMessage.PROJECT_NOT_FOUND_BY_ID));
        List<LastChatUpdated> lastChatUpdatedList = lastChatUpdatedRepository.findAllByProject(project);
        return lastChatUpdatedMapper.convertListToDtos(lastChatUpdatedList);
    }
}
