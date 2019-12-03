package com.softserve.ita.java442.cityDonut.service.impl;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.StorageClient;
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
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

@Service
public class FileStorageServiceImpl implements FileStorageService {
  //  private final Storage storage;
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


            FileInputStream serviceAccount =
                    new FileInputStream("C:\\Users\\Marta\\firebaseConfig.json");

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setStorageBucket("city-donut-app.appspot.com")
                    .build();

            FirebaseApp fireApp = FirebaseApp.initializeApp(options);


            StorageClient storageClient = StorageClient.getInstance(fireApp);
            String blobString = "test/" + "FILE_NAME.EXT";

            storageClient.bucket().create(blobString, file.getInputStream() , Bucket.BlobWriteOption.userProject("city-donut-app"));


            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return savedMediaDto;
        } catch (IOException ex) {
            throw new FileStorageException(fileName + ErrorMessage.COULD_NOT_STORE_FILE);
        }
    }

    public Resource loadFileAsResource(String fileId, long galleryId) {
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

    public List<String> getListOfFilesId(long galleryId) {
        List<MediaDto> mediaDtoList = mediaService.getDtoList(galleryId);
        return getFilesId(mediaDtoList);
    }

    public String getAvatarName(long galleryId) {
        ArrayList<MediaDto> photoDtoList = (ArrayList<MediaDto>) mediaService.getListOfPhotoDto(galleryId);
        MediaDto dto = photoDtoList.get(0);
        return dto.getFileId();
    }

    public boolean delete(long galleryId, String fileName) {
        MediaDto mediaDto = mediaMapper.convertToDto(mediaService.getFileByFileIdAndGalleryId(fileName, galleryId));
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
