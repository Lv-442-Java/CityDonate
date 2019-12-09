package com.softserve.ita.java442.cityDonut.controller;

import com.softserve.ita.java442.cityDonut.service.impl.MediaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class GalleryController {

    private MediaServiceImpl mediaService;

    @Autowired
    public GalleryController(MediaServiceImpl mediaService) {
        this.mediaService = mediaService;
    }

    @GetMapping("/project/{id}/gallery")
    public long getProjectGalleryId(@PathVariable long id) {
        return mediaService.getProjectGalleryId(id);
    }

    @GetMapping("/storyboard/{id}/gallery")
    public long getStoryBoardGalleryId(@PathVariable long id) {
        return mediaService.getStoryBoardGalleryId(id);
    }

    @DeleteMapping("/gallery/{id}")
    public boolean deleteGallery(@PathVariable long id) {
        return mediaService.deleteGallery(id);
    }
}
