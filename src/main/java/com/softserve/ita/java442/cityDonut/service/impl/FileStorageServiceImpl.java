package com.softserve.ita.java442.cityDonut.service.impl;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.StorageClient;
import com.softserve.ita.java442.cityDonut.constant.ErrorMessage;
import com.softserve.ita.java442.cityDonut.dto.media.DownloadFileResponse;
import com.softserve.ita.java442.cityDonut.dto.media.MediaDto;
import com.softserve.ita.java442.cityDonut.exception.FailedToSetCredentialsException;
import com.softserve.ita.java442.cityDonut.exception.FileStorageException;
import com.softserve.ita.java442.cityDonut.mapper.media.MediaMapper;
import com.softserve.ita.java442.cityDonut.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    private static final String BUCKET_NAME = "city-donut-app.appspot.com";
    private static final String DIR = "test/";
    private MediaServiceImpl mediaService;
    private MediaMapper mediaMapper;

    @Autowired
    public FileStorageServiceImpl(MediaServiceImpl mediaService, MediaMapper mediaMapper) {
        this.mediaService = mediaService;
        this.mediaMapper = mediaMapper;
    }

    public MediaDto storeFile(MultipartFile file, long Id) {
        String fileName = file.getOriginalFilename();
        MediaDto mediaDto = new MediaDto();
        try {
            if (fileName.contains("..")) {
                throw new FileStorageException(ErrorMessage.INVALID_CHARACTER + fileName);
            }
            mediaDto.setGalleryId(Id);
            MediaDto savedMediaDto = mediaService.saveMedia(mediaDto, fileName);
            String fileIdWithExt = mediaService.fileIDWithExtension(savedMediaDto);
            StorageClient storageClient = StorageClient.getInstance(initFirebase());
            String DIR = "test/";
            String blobString = DIR + fileIdWithExt;
            String PROJECT_ID = "city-donut-app";
            Blob blob = storageClient.bucket().create(blobString, file.getInputStream(), Bucket.BlobWriteOption.userProject(PROJECT_ID));
            System.out.println(blob.getMediaLink());
            return savedMediaDto;
        } catch (IOException ex) {
            throw new FileStorageException(fileName + ErrorMessage.COULD_NOT_STORE_FILE);
        }
    }

    private FirebaseApp initFirebase() {
        FileInputStream serviceAccount;
        try {
            serviceAccount = new FileInputStream("C:\\Users\\Marta\\firebaseConfig.json");
        } catch (FileNotFoundException e) {
            throw new FileStorageException(ErrorMessage.FILE_NOT_FOUND + "firebaseConfig.json");
        }
        FirebaseOptions options;
        try {
            options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setStorageBucket(BUCKET_NAME)
                    .build();
        } catch (IOException e) {
            e.printStackTrace();
            throw new FailedToSetCredentialsException(ErrorMessage.COULD_NOT_SET_CREDENTIALS);
        }

        return FirebaseApp.initializeApp(options);
    }

    public String formDownloadUrl(String fileId) {
        MediaDto mediaDto = mediaService.getDtoForFile(fileId);
        String FileIdWithExt = mediaService.fileIDWithExtension(mediaDto);
        String url = "https://firebasestorage.googleapis.com/v0/b/city-donut-app.appspot.com/o/test%2F";
        String downloadUrl = url + FileIdWithExt + "?alt=media";
        return downloadUrl;
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
        StorageClient storageClient = StorageClient.getInstance(initFirebase());
        String blobString = DIR + fileName + "." + mediaDto.getExtension().getName();
        Bucket bucket = storageClient.bucket(BUCKET_NAME);
        Blob blob = bucket.get(blobString);
        boolean result = blob.delete();
        if (result) {
            mediaService.deleteInDB(mediaDto);
        }
        return result;
    }

    private ArrayList<String> getFilesId(List<MediaDto> mediaDtoList){
        ArrayList<String> fileNames= new ArrayList<>();
        for (MediaDto dto : mediaDtoList) {
            fileNames.add(dto.getFileId());
        }
        return fileNames;
    }
}
