package com.softserve.ita.java442.cityDonut.service;

import com.softserve.ita.java442.cityDonut.dto.lastChatUpdated.LastChatUpdatedDto;

import java.util.List;

public interface LastChatUpdatedService {
    LastChatUpdatedDto setNewUpdatedTime(long projectId);

    List<LastChatUpdatedDto> getChatUpdatedTimes(long projectId);
}
