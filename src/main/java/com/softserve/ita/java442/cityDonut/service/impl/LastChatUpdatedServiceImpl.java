package com.softserve.ita.java442.cityDonut.service.impl;

import com.softserve.ita.java442.cityDonut.dto.lastChatUpdated.LastChatUpdatedDto;
import com.softserve.ita.java442.cityDonut.mapper.lastChatUpdated.LastChatUpdatedMapper;
import com.softserve.ita.java442.cityDonut.model.LastChatUpdated;
import com.softserve.ita.java442.cityDonut.model.Project;
import com.softserve.ita.java442.cityDonut.model.User;
import com.softserve.ita.java442.cityDonut.repository.LastChatUpdatedRepository;
import com.softserve.ita.java442.cityDonut.repository.ProjectRepository;
import com.softserve.ita.java442.cityDonut.repository.UserRepository;
import com.softserve.ita.java442.cityDonut.service.LastChatUpdatedService;
import com.softserve.ita.java442.cityDonut.service.ProjectService;
import com.softserve.ita.java442.cityDonut.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class LastChatUpdatedServiceImpl implements LastChatUpdatedService {

    @Autowired
    private LastChatUpdatedRepository lastChatUpdatedRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private LastChatUpdatedMapper lastChatUpdatedMapper;

    @Autowired
    private UserRepository userRepository;

    @Override
    public LastChatUpdatedDto setNewUpdatedTime(long projectId) {
        User user = userService.getCurrentUser();
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("error"));
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
        User user;
        try {
            user = userService.getCurrentUser();
        }
        catch (Exception exc) {
            user = userRepository.getUserById(2);
        }
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("error"));
        List<LastChatUpdated> lastChatUpdatedList = lastChatUpdatedRepository.findAllByProject(project);
        return lastChatUpdatedMapper.convertListToDtos(lastChatUpdatedList);
    }
}
