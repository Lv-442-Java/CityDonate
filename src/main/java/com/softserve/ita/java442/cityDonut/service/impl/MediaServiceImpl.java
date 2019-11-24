package com.softserve.ita.java442.cityDonut.service.impl;

import com.softserve.ita.java442.cityDonut.constant.ErrorMessage;
import com.softserve.ita.java442.cityDonut.dto.media.MediaDto;
import com.softserve.ita.java442.cityDonut.exception.FileStorageException;
import com.softserve.ita.java442.cityDonut.mapper.media.MediaMapper;
import com.softserve.ita.java442.cityDonut.model.Extension;
import com.softserve.ita.java442.cityDonut.model.Media;
import com.softserve.ita.java442.cityDonut.model.MediaType;
import com.softserve.ita.java442.cityDonut.repository.*;
import com.softserve.ita.java442.cityDonut.service.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class MediaServiceImpl implements MediaService {

    private MediaTypeRepository mediaTypeRepository;
    private MediaRepository mediaRepository;
    private ExtensionRepository extensionRepository;
    private MediaMapper mediaMapper;
    private ProjectRepository projectRepository;
    private StoryBoardRepository storyBoardRepository;

    @Autowired
    public MediaServiceImpl(MediaTypeRepository mediaTypeRepository, MediaRepository mediaRepository,
                            ExtensionRepository extensionRepository, MediaMapper mediaMapper,
                            ProjectRepository projectRepository, StoryBoardRepository storyBoardRepository) {
        this.mediaTypeRepository = mediaTypeRepository;
        this.mediaRepository = mediaRepository;
        this.extensionRepository = extensionRepository;
        this.mediaMapper = mediaMapper;
        this.projectRepository = projectRepository;
        this.storyBoardRepository = storyBoardRepository;
    }

    @Transactional
    public MediaDto saveMedia(MediaDto mediaDto, String fileName) {
        Media mediaModel = createMediaModelFromDtoData(mediaDto, fileName);
        mediaRepository.save(mediaModel);
        MediaDto savedMediaDto = mediaMapper.convertToDto(mediaRepository.getByFileId(mediaModel.getFileId()));
        return savedMediaDto;
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
        mediaModel.setCreationDate(new Timestamp(new Date().getTime()));
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

    Media getFileByFileIdAndGalleryId(String fileName, long galleryId) {
        Media media = mediaRepository.getFileByFileIdAndGalleryId(fileName, galleryId);
        if (media == null) {
            throw new FileStorageException(ErrorMessage.FILE_NOT_FOUND_BY_NAME_AND_PROJECT_ID + fileName + ", id " + galleryId);
        }
        return media;
    }

    List<MediaDto> getListOfPhotoDto(long galleryId) {
        MediaType mediaType = mediaTypeRepository.findByType("photo");
        return mediaMapper.convertListToDto(mediaRepository.getPhotosByGalleryIdAndMediaType(galleryId, mediaType));
    }

    List<MediaDto> getDtoList(long galleryId) {
        return mediaMapper.convertListToDto(mediaRepository.getFilesByGalleryId(galleryId));
    }

    MediaDto getDtoForFile(String fileId) {
        return mediaMapper.convertToDto(mediaRepository.getByFileId(fileId));
    }

    void deleteInDB(MediaDto dto) {
        Media mediaToDelete = mediaMapper.convertToModel(dto);
        mediaRepository.delete(mediaToDelete);
    }

    public long getProjectGalleryId(long projectId) {
        long galleryId = projectRepository.getById(projectId).getGallery().getId();
        return galleryId;
    }

    public long getStoryBoardGalleryId(long storyBoardId) {
        long galleryId = storyBoardRepository.getOne(storyBoardId).getGallery().getId();
        return galleryId;
    }
}
