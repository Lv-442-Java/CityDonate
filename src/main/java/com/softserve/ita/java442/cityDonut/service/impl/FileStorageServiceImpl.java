package com.softserve.ita.java442.cityDonut.service.impl;

import com.softserve.ita.java442.cityDonut.constant.ErrorMessage;
import com.softserve.ita.java442.cityDonut.dto.media.DownloadFileResponse;
import com.softserve.ita.java442.cityDonut.dto.media.FileStorageProperties;
import com.softserve.ita.java442.cityDonut.dto.media.MediaDto;
import com.softserve.ita.java442.cityDonut.exception.FileStorageException;
import com.softserve.ita.java442.cityDonut.mapper.media.MediaMapper;
import com.softserve.ita.java442.cityDonut.repository.MediaRepository;
import com.softserve.ita.java442.cityDonut.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

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
    MediaMapper mediaMapper;

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
    public MediaDto storeFile(MultipartFile file, long Id) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        MediaDto mediaDto = new MediaDto();
        try {
            if (fileName.contains("..")) {
                throw new FileStorageException(ErrorMessage.INVALID_CHARACTER + fileName);
            }
            mediaDto.setGalleryId(Id);
            MediaDto savedMediaDto = mediaService.saveMedia(mediaDto, fileName);
            String fileIdWithExt = mediaService.fileIDWithExtension(savedMediaDto);
            Path targetLocation = this.fileStorageLocation.resolve(fileIdWithExt);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return savedMediaDto;
        } catch (IOException ex) {
            throw new FileStorageException(fileName + ErrorMessage.COULD_NOT_STORE_FILE);
        }
    }

    public Resource loadFileAsResource(String fileId, long projectId) {
        MediaDto mediaDto = mediaService.getDtoForFile(fileId);
        try {
            String FileIdWithExt = mediaService.fileIDWithExtension(mediaDto);
            Path filePath = this.fileStorageLocation.resolve(FileIdWithExt).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new FileStorageException(ErrorMessage.FILE_NOT_FOUND + mediaDto.getName());
            }
        } catch (MalformedURLException ex) {
            throw new FileStorageException(ErrorMessage.FILE_NOT_FOUND + mediaDto.getName());
        }
    }

    public DownloadFileResponse getFile (String fileId){
        MediaDto mediaDto = mediaService.getDtoForFile(fileId);
        DownloadFileResponse fileResponse = new DownloadFileResponse();
        fileResponse.setFileName(mediaDto.getName());
        fileResponse.setMediaType(mediaDto.getMediaType().getType());
        fileResponse.setGalleryId(mediaDto.getGalleryId());
        return fileResponse;
    }

    public List<String> getPhotosId(long projectId) {
        List<MediaDto> mediaDtoList = mediaService.getListOfPhotoDto(projectId);
        return getFilesId(mediaDtoList);
    }

    public List<String> getListOfFilesId(long projectId) {
        List<MediaDto> mediaDtoList = mediaService.getDtoList(projectId);
        return getFilesId(mediaDtoList);
    }

    public String getAvatarName(long projectId) {
        ArrayList<MediaDto> photoDtoList = (ArrayList<MediaDto>) mediaService.getListOfPhotoDto(projectId);
        MediaDto dto = photoDtoList.get(0);
        return dto.getName();
    }

    public boolean delete(long projectId, String fileName) {
        MediaDto mediaDto = mediaMapper.convertToDto(mediaService.getFileByNameAndProjectId(fileName, projectId));
        String FileIdWithExt = mediaService.fileIDWithExtension(mediaDto);
        Path filePath = this.fileStorageLocation.resolve(FileIdWithExt).normalize();
        File file = new File(String.valueOf(filePath));
        if (file.delete()) {
            mediaService.deleteInDB(mediaDto);
            return true;
        } else {
            throw new FileStorageException(ErrorMessage.FILE_NOT_FOUND + fileName);
        }
    }

    private ArrayList<String> getFilesId(List<MediaDto> mediaDtoList){
        ArrayList<String> fileNames= new ArrayList<>();
        for (MediaDto dto : mediaDtoList) {
            fileNames.add(dto.getFileId());
        }
        return fileNames;
    }

}
