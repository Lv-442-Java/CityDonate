package com.softserve.ita.java442.cityDonut.service.impl;

import com.softserve.ita.java442.cityDonut.repository.StoryBoardRepository;
import com.softserve.ita.java442.cityDonut.service.StoryBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StoryBoardServiceImpl implements StoryBoardService {

    @Autowired
    StoryBoardRepository storyBoardRepository;
}
