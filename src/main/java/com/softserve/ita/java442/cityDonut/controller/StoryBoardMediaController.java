package com.softserve.ita.java442.cityDonut.controller;

import com.softserve.ita.java442.cityDonut.dto.media.DownloadFileResponse;
import com.softserve.ita.java442.cityDonut.dto.media.UploadFileResponse;
import com.softserve.ita.java442.cityDonut.repository.StoryBoardRepository;
import com.softserve.ita.java442.cityDonut.service.impl.GalleryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/storyboard/{id}")
public class StoryBoardMediaController {

    private StoryBoardRepository storyBoardRepository;
    private GalleryImpl galleryImlp;

    @Autowired
   public StoryBoardMediaController(StoryBoardRepository storyBoardRepository, GalleryImpl galleryImlp) {
        this.storyBoardRepository = storyBoardRepository;
        this.galleryImlp = galleryImlp;
    }

    @PostMapping("/uploadFile")
    public UploadFileResponse uploadFile( MultipartFile file, @PathVariable("id")  long id) {
        long galleryId = storyBoardRepository.getOne(id).getGallery().getId();
        return galleryImlp.uploadFile(file,id,galleryId);
    }

    @PostMapping("/uploadMultipleFiles")
    public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files, @PathVariable("id") long id) {
        return Arrays.asList(files)
                .stream()
                .map(file -> uploadFile(file, id))
                .collect(Collectors.toList());
    }

    @GetMapping("/fileInfo")
    public List<DownloadFileResponse> getAllFilesInfo(@PathVariable("id") long id){
        long galleryId = storyBoardRepository.getOne(id).getGallery().getId();
        return galleryImlp.getAllFilesInfo(id, galleryId);
    }

    @GetMapping("/downloadFile/{fileId:.+}")
    public ResponseEntity<Resource> getResource(@PathVariable("id") long id, HttpServletRequest request, @PathVariable String fileId) {
        long galleryId = storyBoardRepository.getOne(id).getGallery().getId();
        return galleryImlp.getResource(id, request, fileId, galleryId);
    }

    @GetMapping("/getUrl")
    public ResponseEntity<List<String>> photoLinks(@PathVariable("id") long id) {
        long galleryId = storyBoardRepository.getOne(id).getGallery().getId();
        return galleryImlp.photoLinks(id, galleryId);
    }

    @GetMapping("/getAvatar")
    public ResponseEntity<String> avatarLink(@PathVariable("id") long id) {
        long galleryId = storyBoardRepository.getOne(id).getGallery().getId();
        return galleryImlp.avatarLink(id, galleryId);
    }

    @DeleteMapping("/deleteFile/{fileName:.+}")
    public ResponseEntity<List<String>> deleteFile(@PathVariable("id") long id, @PathVariable String fileName) {
        long galleryId = storyBoardRepository.getOne(id).getGallery().getId();
        return galleryImlp.deleteFile(id, fileName, galleryId);
    }
}
