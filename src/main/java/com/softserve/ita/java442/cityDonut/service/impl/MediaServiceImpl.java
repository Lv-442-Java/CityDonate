package com.softserve.ita.java442.cityDonut.service.impl;

import com.softserve.ita.java442.cityDonut.constant.ErrorMessage;
import com.softserve.ita.java442.cityDonut.dto.media.MediaDto;
import com.softserve.ita.java442.cityDonut.exception.FileStorageException;
import com.softserve.ita.java442.cityDonut.mapper.media.MediaMapper;
import com.softserve.ita.java442.cityDonut.model.Extension;
import com.softserve.ita.java442.cityDonut.model.Media;
import com.softserve.ita.java442.cityDonut.model.MediaType;
import com.softserve.ita.java442.cityDonut.repository.ExtensionRepository;
import com.softserve.ita.java442.cityDonut.repository.MediaRepository;
import com.softserve.ita.java442.cityDonut.repository.MediaTypeRepository;
import com.softserve.ita.java442.cityDonut.service.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class MediaServiceImpl implements MediaService {

    @Autowired
    MediaTypeRepository mediaTypeRepository;

    @Autowired
    MediaRepository mediaRepository;

    @Autowired
    ExtensionRepository extensionRepository;

    @Autowired
    MediaMapper mediaMapper;

    @Transactional
    public void saveMedia(MediaDto mediaDto, String fileName) {
        Media mediaModel = createMediaModelFromDtoData(mediaDto, fileName);
        mediaRepository.save(mediaModel);
    }

    private Media createMediaModelFromDtoData(MediaDto mediaDto, String fileName) {
        mediaDto.setName(fileName);
        mediaDto.setFileId(generateFileId());
        String ext = getFileExtension(fileName);
        Extension extension = extensionRepository.findByName(ext);
        mediaDto.setExtension(extension);
        MediaType mediaType = extension.getMediaType();
        mediaDto.setMediaType(mediaType);
        Media mediaModel = mediaMapper.convertToModel(mediaDto);
        mediaModel.setCreationDate(LocalDateTime.now());
        return mediaModel;
    }

    String fileIDWithExtension(MediaDto mediaDto) {
        List<String> name = Arrays.asList(mediaDto.getFileId(), mediaDto.getExtension().getName());
        return String.join(".", name);
    }

    private String getFileExtension(String fileName) {
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
            return fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        else return "";
    }

    private String generateFileId() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    Media getFileByNameAndProjectId(String fileName, long projectId) {
        Media media = mediaRepository.findByNameAndProjectId(fileName, projectId);
        if (media == null) {
            throw new FileStorageException(ErrorMessage.FILE_NOT_FOUND_BY_NAME_AND_PROJECT_ID + fileName + ", id " + projectId);
        }
        return media;
    }

    List<MediaDto> getPhotoNames(long projectId) {
        MediaType mediaType = mediaTypeRepository.findByType("photo");
        return mediaMapper.convertListToDto(mediaRepository.getPhotosByProjectIdAndMediaType(projectId, mediaType));
    }

    List<MediaDto> getFileNames(long projectId) {
        return mediaMapper.convertListToDto(mediaRepository.getFilesByProjectId(projectId));
    }

    void deleteInDB(MediaDto dto) {
        Media mediaToDelete = mediaMapper.convertToModel(dto);
        mediaRepository.delete(mediaToDelete);
    }
}
