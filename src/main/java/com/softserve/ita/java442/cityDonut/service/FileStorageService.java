package com.softserve.ita.java442.cityDonut.service;

import com.softserve.ita.java442.cityDonut.dto.media.DownloadFileResponse;
import com.softserve.ita.java442.cityDonut.dto.media.MediaDto;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileStorageService {

    MediaDto storeFile(MultipartFile file, long projectId);

    //Resource loadFileAsResource(String fileName, long fileId);

    DownloadFileResponse getFile(String fileId);

    List<String> getListOfFilesId(long galleryId);

    String getAvatarName(long galleryId);

    boolean delete(long galleryId, String fileName);
}
