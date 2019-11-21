package com.softserve.ita.java442.cityDonut.service.impl;

import com.softserve.ita.java442.cityDonut.constant.ErrorMessage;
import com.softserve.ita.java442.cityDonut.dto.media.FileUpload;
import com.softserve.ita.java442.cityDonut.dto.media.MediaDto;
import com.softserve.ita.java442.cityDonut.exception.NotFoundException;
import com.softserve.ita.java442.cityDonut.service.GalleryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;

@Service
public class GalleryServiceImpl implements GalleryService {

    @Autowired
    private FileStorageServiceImpl fileStorageService;

    public FileUpload uploadFile(MultipartFile file, long galleryId) {
        FileUpload fileUpload = new FileUpload();
        MediaDto responseDto = fileStorageService.storeFile(file, galleryId);
        fileUpload.setFileName(responseDto.getName());
        fileUpload.setFileId(responseDto.getFileId());
        fileUpload.setFileType(responseDto.getMediaType().getType());
        return fileUpload;
    }

    public ResponseEntity<Resource> getResource(HttpServletRequest request, String fileId, long galleryId) {
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

    public ArrayList<String> deleteFile(long id, String fileName, long galleryId) {
        boolean isRemoved = fileStorageService.delete(galleryId, fileName);
        if (!isRemoved) {
            throw new NotFoundException(ErrorMessage.FILE_NOT_FOUND + fileName);
        }
        ArrayList<String> filesId = (ArrayList<String>) fileStorageService.getListOfFilesId(galleryId);
        return filesId;
    }
}
