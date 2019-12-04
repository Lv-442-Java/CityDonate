package com.softserve.ita.java442.cityDonut.controller;

import com.softserve.ita.java442.cityDonut.constant.ErrorMessage;
import com.softserve.ita.java442.cityDonut.dto.media.DownloadFileResponse;
import com.softserve.ita.java442.cityDonut.dto.media.MediaDto;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/gallery/{id}")
public class MediaController {

    @Autowired
    private FileStorageServiceImpl fileStorageService;

    private UploadFileResponse uploadFile(MultipartFile file, long id) {
        MediaDto responseDto = fileStorageService.storeFile(file, id);
        String fileId = responseDto.getFileId();
        String fileName = responseDto.getName();
        String mediaType = responseDto.getMediaType().getType();
        String fileDownloadUri = buildDownloadUri(id, fileId);
        return new UploadFileResponse(fileName, fileDownloadUri,
                mediaType, file.getSize());
    }

    @PostMapping("/")
    public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files, @PathVariable("id") long id) {
        return Arrays.stream(files)
                .map(file -> uploadFile(file, id))
                .collect(Collectors.toList());
    }

    private DownloadFileResponse getFileInfo(long id,String fileId) {
        String fileDownloadUri = buildDownloadUri(id, fileId);
        DownloadFileResponse fileResponse = fileStorageService.getFile(fileId);
        fileResponse.setFileDownloadUri(fileDownloadUri);
        return fileResponse;
    }

    @GetMapping("/")
    public List<DownloadFileResponse> getAllFilesInfo(@PathVariable("id") long id){
        ArrayList<String> filesId = (ArrayList<String>) fileStorageService.getListOfFilesId(id);
        ArrayList<DownloadFileResponse> result = new ArrayList<>();
        for (String fileId : filesId) {
            result.add(getFileInfo(id, fileId));
        }
        return result;
    }

    @GetMapping("/{fileId:.+}")
    public ResponseEntity<String> getResource(@PathVariable("id") long id, @PathVariable String fileId)  {
       String response = fileStorageService.loadFileAsResource(fileId);
       return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/getAvatar")
    public ResponseEntity<String> avatarLink(@PathVariable("id") long id) {
        String fileName = fileStorageService.getAvatarName(id);
        return ResponseEntity.status(HttpStatus.OK).body(buildDownloadUri(id, fileName));
    }

    @DeleteMapping("/{fileId:.+}")
    public ResponseEntity<List<String>> deleteFile(@PathVariable("id") long id, @PathVariable String fileId) {
        boolean isRemoved = fileStorageService.delete(id, fileId);
        if (!isRemoved) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        ArrayList<String> filesId = (ArrayList<String>) fileStorageService.getListOfFilesId(id);
        ArrayList<String> result = new ArrayList<>();
        for (String newFileId : filesId) {
            result.add(buildDownloadUri(id, newFileId));
        }
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    private String buildDownloadUri(long id, String fileId) {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/v1/gallery/")
                .path(String.valueOf(id))
                .path("/")
                .path(fileId)
                .toUriString();
    }
}
