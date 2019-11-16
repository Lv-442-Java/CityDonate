package com.softserve.ita.java442.cityDonut.controller;

import com.softserve.ita.java442.cityDonut.constant.ErrorMessage;
import com.softserve.ita.java442.cityDonut.dto.media.UploadFileResponse;
import com.softserve.ita.java442.cityDonut.exception.NotFoundException;
import com.softserve.ita.java442.cityDonut.service.impl.FileStorageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/project/{id}")
public class FileController {

    private FileStorageServiceImpl fileStorageService;

    @Autowired
    public FileController(FileStorageServiceImpl fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @PostMapping("/uploadFile")
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file, @PathVariable("id") long id) {

        String fileName = fileStorageService.storeFile(file, id);
        String url = "/api/v1/project/";
        String download = "/downloadFile/";
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(url)
                .path(String.valueOf(id))
                .path(download)
                .path(fileName)
                .toUriString();

        return new UploadFileResponse(fileName, fileDownloadUri,
                file.getContentType(), file.getSize());
    }

    @PostMapping("/uploadMultipleFiles")
    public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files, @PathVariable("id") long id) {
        return Arrays.asList(files)
                .stream()
                .map(file -> uploadFile(file, id))
                .collect(Collectors.toList());
    }

    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable("id") long id, HttpServletRequest request, @PathVariable String fileName) {
        Resource resource = fileStorageService.loadFileAsResource(fileName, id);
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
    public ResponseEntity<List<String>> photoLinks(@PathVariable("id") long id){
        return ResponseEntity.status(HttpStatus.OK).body(fileStorageService.getPhotoDownloadUrl(id));
    }

    @GetMapping("/getAvatar")
    public ResponseEntity<String> avatarLink(@PathVariable("id") long id){
        return ResponseEntity.status(HttpStatus.OK).body(fileStorageService.getAvatarDownloadLink(id));
    }

    @DeleteMapping("/deleteFile/{fileName:.+}")
    public ResponseEntity<List<String>> deletePost(@PathVariable("id") long id, @PathVariable String fileName) {
        boolean isRemoved = fileStorageService.delete(id, fileName);
        if (!isRemoved) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.status(HttpStatus.OK).body(fileStorageService.getFileDownloadUrl(id));
    }
}
