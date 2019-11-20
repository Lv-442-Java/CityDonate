package com.softserve.ita.java442.cityDonut.controller;

import com.softserve.ita.java442.cityDonut.constant.ErrorMessage;
import com.softserve.ita.java442.cityDonut.dto.media.DownloadFileResponse;
import com.softserve.ita.java442.cityDonut.dto.media.MediaDto;
import com.softserve.ita.java442.cityDonut.dto.media.UploadFileResponse;
import com.softserve.ita.java442.cityDonut.exception.NotFoundException;
import com.softserve.ita.java442.cityDonut.repository.ProjectRepository;
import com.softserve.ita.java442.cityDonut.service.impl.FileStorageServiceImpl;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("project/{id}")
public class ProjectMediaController {

    private ProjectRepository projectRepository;
    private FileStorageServiceImpl fileStorageService;

    public ProjectMediaController(ProjectRepository projectRepository, FileStorageServiceImpl fileStorageService) {
        this.projectRepository = projectRepository;
        this.fileStorageService = fileStorageService;
    }


    @PostMapping("/uploadFile")
        public UploadFileResponse uploadFile( MultipartFile file,  long id) {
        long galleryId = projectRepository.getById(id).getGallery().getId();
        MediaDto responseDto = fileStorageService.storeFile(file, galleryId);
        String fileId = responseDto.getFileId();
        String fileName = responseDto.getName();
        String mediaType = responseDto.getMediaType().getType();
        String fileDownloadUri = buildDownloadUri(id, fileId);
        return new UploadFileResponse(fileName, fileDownloadUri,
                mediaType, file.getSize());
    }

    @PostMapping("/uploadMultipleFiles")
    public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files, @PathVariable("id") long id) {
        return Arrays.asList(files)
                .stream()
                .map(file -> uploadFile(file, id))
                .collect(Collectors.toList());
    }

    private DownloadFileResponse getFileInfo(@PathVariable("id") long id, @PathVariable String fileId) {
        //long galleryId = projectRepository.getById(id).getGallery().getId();
        String fileDownloadUri = buildDownloadUri(id, fileId);
        DownloadFileResponse fileResponse = fileStorageService.getFile(fileId);
        fileResponse.setFileDownloadUri(fileDownloadUri);
        return fileResponse;
    }

    @GetMapping("/fileInfo")
    public List<DownloadFileResponse> getAllFilesInfo(@PathVariable("id") long id){
        long galleryId = projectRepository.getById(id).getGallery().getId();
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
        Resource resource = fileStorageService.loadFileAsResource(fileId, galleryId);
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            throw new NotFoundException(ErrorMessage.NOT_DETERMINED_FILE_TYPE);
        }
        if (contentType == null) {
            contentType = "application/octet-stream";
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
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
        boolean isRemoved = fileStorageService.delete(galleryId, fileName);
        if (!isRemoved) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        ArrayList<String> filesId = (ArrayList<String>) fileStorageService.getListOfFilesId(galleryId);
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
