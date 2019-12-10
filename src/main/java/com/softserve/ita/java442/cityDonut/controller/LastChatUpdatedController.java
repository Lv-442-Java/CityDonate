package com.softserve.ita.java442.cityDonut.controller;

import com.softserve.ita.java442.cityDonut.dto.lastChatUpdated.LastChatUpdatedDto;
import com.softserve.ita.java442.cityDonut.service.LastChatUpdatedService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/api/v1/chatupdated")
public class LastChatUpdatedController {

    @Autowired
    private LastChatUpdatedService lastChatUpdatedService;

    @PutMapping("/{projectId}")
    public ResponseEntity<LastChatUpdatedDto> onChatUpdated(@PathVariable long projectId) {
        return ResponseEntity.ok(lastChatUpdatedService.setNewUpdatedTime(projectId));
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<List<LastChatUpdatedDto>> getLastUpdatedTimes(@PathVariable long projectId) {
        return ResponseEntity.ok(lastChatUpdatedService.getChatUpdatedTimes(projectId));
    }

}
