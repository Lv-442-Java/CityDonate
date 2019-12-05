package com.softserve.ita.java442.cityDonut.controller;

import com.softserve.ita.java442.cityDonut.dto.media.DownloadFileResponse;
import com.softserve.ita.java442.cityDonut.dto.media.MediaDto;
import com.softserve.ita.java442.cityDonut.dto.media.UploadFileResponse;
import com.softserve.ita.java442.cityDonut.service.impl.FileStorageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/gallery")
public class MediaController {

    @Autowired
    private FileStorageServiceImpl fileStorageService;

    private UploadFileResponse uploadFile(MultipartFile file, long id) {
        MediaDto responseDto = fileStorageService.storeFile(file, id);
        String fileId = responseDto.getFileId();
        String fileName = responseDto.getName();
        String mediaType = responseDto.getMediaType().getType();
        String fileDownloadUri = fileStorageService.formDownloadUrl(fileId);
        return new UploadFileResponse(fileName, fileDownloadUri,
                mediaType, file.getSize());
    }

    @PostMapping("/{id}/")
    public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files, @PathVariable("id") long id) {
        return Arrays.stream(files)
                .map(file -> uploadFile(file, id))
                .collect(Collectors.toList());
    }

    private DownloadFileResponse getFileInfo(String fileId) {
        String fileDownloadUri = fileStorageService.formDownloadUrl(fileId);
        DownloadFileResponse fileResponse = fileStorageService.getFile(fileId);
        fileResponse.setFileDownloadUri(fileDownloadUri);
        return fileResponse;
    }

    @GetMapping("/{id}/")
    public List<DownloadFileResponse> getAllFilesInfo(@PathVariable("id") long id){
        ArrayList<String> filesId = (ArrayList<String>) fileStorageService.getListOfFilesId(id);
        ArrayList<DownloadFileResponse> result = new ArrayList<>();
        for (String fileId : filesId) {
            result.add(getFileInfo(fileId));
        }
        return result;
    }

    @GetMapping("/{fileId:.+}")
    public ResponseEntity<String> getResource(@PathVariable String fileId)  {
       String response = fileStorageService.formDownloadUrl(fileId);
       return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}/getAvatar")
    public ResponseEntity<String> avatarLink(@PathVariable("id") long id) {
        String fileName = fileStorageService.getAvatarName(id);
        return ResponseEntity.status(HttpStatus.OK).body(fileStorageService.formDownloadUrl(fileName));
    }

    @DeleteMapping("/{id}/{fileId:.+}")
    public ResponseEntity<List<String>> deleteFile(@PathVariable("id") long id, @PathVariable String fileId) {
        boolean isRemoved = fileStorageService.delete(id, fileId);
        if (!isRemoved) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        ArrayList<String> filesId = (ArrayList<String>) fileStorageService.getListOfFilesId(id);
        ArrayList<String> result = new ArrayList<>();
        for (String newFileId : filesId) {
            result.add(fileStorageService.formDownloadUrl(newFileId));
        }
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
