package com.softserve.ita.java442.cityDonut.service.impl;

import com.softserve.ita.java442.cityDonut.dto.gallery.GalleryDto;
import com.softserve.ita.java442.cityDonut.dto.media.MediaDto;
import com.softserve.ita.java442.cityDonut.mapper.gallery.GalleryMapper;
import com.softserve.ita.java442.cityDonut.model.Gallery;
import com.softserve.ita.java442.cityDonut.repository.GalleryRepository;
import com.softserve.ita.java442.cityDonut.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

public class GalleryServiceImpl {
    @Autowired
    GalleryRepository galleryRepository;

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    FileStorageServiceImpl fileStorageService;

    @Autowired
    GalleryMapper galleryMapper;

    public void addMedia(long projectId, MultipartFile file){
        long galleryId = projectRepository.getById(projectId).getGallery().getId();
        MediaDto mediaDto =  fileStorageService.storeFile(file, galleryId);

    }
}
