package com.softserve.ita.java442.cityDonut.service.impl;

import com.softserve.ita.java442.cityDonut.constant.ErrorMessage;
import com.softserve.ita.java442.cityDonut.dto.media.FileStorageProperties;
import com.softserve.ita.java442.cityDonut.dto.media.MediaDto;
import com.softserve.ita.java442.cityDonut.exception.FileStorageException;
import com.softserve.ita.java442.cityDonut.model.Media;
import com.softserve.ita.java442.cityDonut.repository.MediaRepository;
import com.softserve.ita.java442.cityDonut.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileStorageServiceImpl implements FileStorageService {
    private final Path fileStorageLocation;
    @Autowired
    FileStorageServiceImpl fileStorage;

    @Autowired
    MediaServiceImpl mediaService;

    @Autowired
    MediaRepository mediaRepository;

    @Autowired
    public FileStorageServiceImpl(FileStorageProperties fileStorageProperties) {

        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException(ErrorMessage.FILE_NOT_FOUND);
        }
    }

    @Override
    public String storeFile(MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            if (fileName.contains("..")) {
                throw new FileStorageException(ErrorMessage.INVALID_CHARACTER + fileName);
            }
            MediaDto mediaDto = new MediaDto();
//            mediaDto.setName(fileName);
//            mediaDto.setFileId(generateFileId());
//            mediaDto.setExtension(getFileExtension(fileName));
            mediaService.saveMedia(mediaDto, fileName);
            String FileIdWithExt = mediaService.fileIDWithExtension(mediaDto);
            Path targetLocation = this.fileStorageLocation.resolve(FileIdWithExt);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        } catch (IOException ex) {
            throw new FileStorageException(fileName + ErrorMessage.COULD_NOT_STORE_FILE);
        }
    }

    @Override
    public Resource loadFileAsResource(String fileName) {

        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new FileStorageException(ErrorMessage.FILE_NOT_FOUND + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new FileStorageException(ErrorMessage.FILE_NOT_FOUND + fileName);
        }
    }

    public Media getFile(String fileId) {
        return mediaRepository.findById(fileId);

        ///дописати ексепшн
         //       .orElseThrow(() -> new MyFileNotFoundException("File not found with id " + fileId));
    }

//    private String getFileExtension(String fileName) {
//        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
//            return fileName.substring(fileName.lastIndexOf(".") + 1);
//        else return "";
//    }
//
//    private String generateFileId(){
//        UUID uuid = UUID.randomUUID();
//        return uuid.toString();
//    }
}