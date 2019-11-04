package com.softserve.ita.java442.cityDonut.controller;

import com.softserve.ita.java442.cityDonut.dto.storyBoard.StoryBoardDto;
import com.softserve.ita.java442.cityDonut.service.impl.StoryBoardServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/api/v1/project/{id}/storyboard")
public class StoryBoardController {

    @Autowired
    private StoryBoardServiceImpl service;

    @GetMapping()
    public ResponseEntity<List<StoryBoardDto>> getStoryBoardByProject(@PathVariable("id") long projectId){
        return ResponseEntity.status(HttpStatus.OK).body(service.getStoryBoardsByProject(projectId));
    }

    @PostMapping()
    public ResponseEntity<StoryBoardDto> createStoryBoard(@RequestBody StoryBoardDto storyBoardDto){
        StoryBoardDto storyBoard = service.createStoryBoard(storyBoardDto);
        return new ResponseEntity<>(storyBoard, HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<StoryBoardDto> editStoryBoard(@RequestBody StoryBoardDto storyBoardDto){
        return ResponseEntity.status(HttpStatus.OK).body(service. editStoryBoard(storyBoardDto));
    }
}
