package com.softserve.ita.java442.cityDonut.service.impl;

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
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class GalleryImpl {

    @Autowired
    private FileStorageServiceImpl fileStorageService;

    public UploadFileResponse uploadFile(MultipartFile file, long id, long galleryId) {
        MediaDto responseDto = fileStorageService.storeFile(file, galleryId);
        String fileId = responseDto.getFileId();
        String fileName = responseDto.getName();
        String mediaType = responseDto.getMediaType().getType();
        String fileDownloadUri = buildDownloadUri(id, fileId);
        return new UploadFileResponse(fileName, fileDownloadUri,
                mediaType, file.getSize());
    }

    private DownloadFileResponse getFileInfo(long id,String fileId) {
        String fileDownloadUri = buildDownloadUri(id, fileId);
        DownloadFileResponse fileResponse = fileStorageService.getFile(fileId);
        fileResponse.setFileDownloadUri(fileDownloadUri);
        return fileResponse;
    }

    public List<DownloadFileResponse> getAllFilesInfo(long id, long galleryId){
        ArrayList<String> filesId = (ArrayList<String>) fileStorageService.getListOfFilesId(galleryId);
        ArrayList<DownloadFileResponse> result = new ArrayList<>();
        for (String fileId : filesId) {
            result.add(getFileInfo(id, fileId));
        }
        return result;
    }

    public ResponseEntity<Resource> getResource(long id, HttpServletRequest request, String fileId, long galleryId) {
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

    public ResponseEntity<List<String>> photoLinks( long id, long galleryId) {
        ArrayList<String> photoNames = (ArrayList<String>) fileStorageService.getPhotosId(galleryId);
        ArrayList<String> result = new ArrayList<>();
        for (String name : photoNames) {
            result.add(buildDownloadUri(id, name));
        }
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    public ResponseEntity<String> avatarLink(long id, long galleryId) {
        String fileName = fileStorageService.getAvatarName(galleryId);
        return ResponseEntity.status(HttpStatus.OK).body(buildDownloadUri(id, fileName));
    }

    public ResponseEntity<List<String>> deleteFile(long id, String fileName, long galleryId) {
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
