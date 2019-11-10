package com.softserve.ita.java442.cityDonut.service;

import com.softserve.ita.java442.cityDonut.model.Media;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {

    String storeFile(MultipartFile file, long projectId);

    Resource loadFileAsResource(String fileName, long fileId);
}
