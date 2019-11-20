package com.softserve.ita.java442.cityDonut.service;

import com.softserve.ita.java442.cityDonut.dto.media.FileUpload;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

public interface GalleryService {

    FileUpload uploadFile(MultipartFile file, long galleryId);

    ResponseEntity<Resource> getResource(HttpServletRequest request, String fileId, long galleryId);

    ArrayList<String> deleteFile(long id, String fileName, long galleryId);
}
