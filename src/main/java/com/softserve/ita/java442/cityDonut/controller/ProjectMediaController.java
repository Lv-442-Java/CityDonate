package com.softserve.ita.java442.cityDonut.controller;

import com.softserve.ita.java442.cityDonut.dto.media.DownloadFileResponse;
import com.softserve.ita.java442.cityDonut.dto.media.FileUpload;
import com.softserve.ita.java442.cityDonut.dto.media.UploadFileResponse;
import com.softserve.ita.java442.cityDonut.repository.ProjectRepository;
import com.softserve.ita.java442.cityDonut.service.impl.FileStorageServiceImpl;
import com.softserve.ita.java442.cityDonut.service.impl.GalleryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/project/{id}")
public class ProjectMediaController {

    private ProjectRepository projectRepository;
    private GalleryImpl galleryImpl;
    private FileStorageServiceImpl fileStorageService;

    @Autowired
    public ProjectMediaController(ProjectRepository projectRepository, GalleryImpl galleryImpl, FileStorageServiceImpl fileStorageService) {
        this.projectRepository = projectRepository;
        this.galleryImpl = galleryImpl;
        this.fileStorageService = fileStorageService;
    }

    @PostMapping("/uploadFile")
    public UploadFileResponse uploadFile(MultipartFile file, @PathVariable("id") long id) {
        long galleryId = projectRepository.getById(id).getGallery().getId();
        FileUpload fileUpload = galleryImpl.uploadFile(file, galleryId);
        String downloadUrl = buildDownloadUri(id, fileUpload.getFileId());
        return new UploadFileResponse(fileUpload.getFileName(), downloadUrl,
                fileUpload.getFileType(), file.getSize());
    }

    @PostMapping("/uploadMultipleFiles")
    public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files, @PathVariable("id") long id) {
        return Arrays.asList(files)
                .stream()
                .map(file -> uploadFile(file, id))
                .collect(Collectors.toList());
    }

    private DownloadFileResponse getFileInfo(long id,String fileId) {
        String fileDownloadUri = buildDownloadUri(id, fileId);
        DownloadFileResponse fileResponse = fileStorageService.getFile(fileId);
        fileResponse.setFileDownloadUri(fileDownloadUri);
        return fileResponse;
    }

    @GetMapping("/fileInfo")
    public List<DownloadFileResponse> getAllFilesInfo(long id, long galleryId){
        ArrayList<String> filesId = (ArrayList<String>) fileStorageService.getListOfFilesId(galleryId);
        ArrayList<DownloadFileResponse> result = new ArrayList<>();
        for (String fileId : filesId) {
            result.add(getFileInfo(id, fileId));
        }
        return result;
    }

    @GetMapping("/downloadFile/{fileId:.+}")
    public ResponseEntity<Resource> getResource(@PathVariable("id") long id, HttpServletRequest request, @PathVariable String fileId) {
        long galleryId = projectRepository.getById(id).getGallery().getId();
        return galleryImpl.getResource(request, fileId, galleryId);
    }

    @GetMapping("/getUrl")
    public ResponseEntity<List<String>> photoLinks(@PathVariable("id") long id) {
        long galleryId = projectRepository.getById(id).getGallery().getId();
        ArrayList<String> photoNames = (ArrayList<String>) fileStorageService.getPhotosId(galleryId);
        ArrayList<String> result = new ArrayList<>();
        for (String name : photoNames) {
            result.add(buildDownloadUri(id, name));
        }
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/getAvatar")
    public ResponseEntity<String> avatarLink(@PathVariable("id") long id) {
        long galleryId = projectRepository.getById(id).getGallery().getId();
        String fileName = fileStorageService.getAvatarName(galleryId);
        return ResponseEntity.status(HttpStatus.OK).body(buildDownloadUri(id, fileName));
    }

    @DeleteMapping("/deleteFile/{fileName:.+}")
    public ResponseEntity<List<String>> deleteFile(@PathVariable("id") long id, @PathVariable String fileName) {
        long galleryId = projectRepository.getById(id).getGallery().getId();
        ArrayList<String> filesId = galleryImpl.deleteFile(id, fileName, galleryId);
        ArrayList<String> result = new ArrayList<>();
        for (String fileId : filesId) {
            result.add(buildDownloadUri(id, fileId));
        }
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    private String buildDownloadUri(long id, String fileName) {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/v1/project/")
                .path(String.valueOf(id))
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();
    }
}
