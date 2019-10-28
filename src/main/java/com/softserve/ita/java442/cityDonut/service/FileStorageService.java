package com.softserve.ita.java442.cityDonut.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;

public interface FileStorageService {

    String storeFile(MultipartFile file);

    Resource loadFileAsResource(String fileName) throws MalformedURLException;
}
