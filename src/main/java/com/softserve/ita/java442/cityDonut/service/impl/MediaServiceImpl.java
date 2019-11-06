package com.softserve.ita.java442.cityDonut.service.impl;

import com.softserve.ita.java442.cityDonut.dto.media.MediaDto;
import com.softserve.ita.java442.cityDonut.mapper.media.MediaMapper;
import com.softserve.ita.java442.cityDonut.model.Media;
import com.softserve.ita.java442.cityDonut.repository.MediaRepository;
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
    MediaRepository mediaRepository;

    @Autowired
    private MediaTypeServiceImpl mediaTypeService;

    @Autowired
    private MediaMapper mediaMapper;

    @Transactional
    public MediaDto saveMedia(MediaDto mediaDto, String fileName) {
        Media mediaModel = createMediaModelFromDtoData(mediaDto, fileName);
        Media resultOfQuery = mediaRepository.save(mediaModel);
        MediaDto result = mediaMapper.convertToDto(resultOfQuery);
        mediaRepository.flush();
        return result;
    }

    private Media createMediaModelFromDtoData(MediaDto mediaDto, String fileName) {
        mediaDto.setName(fileName);
        mediaDto.setFileId(generateFileId());
        mediaDto.setExtension(getFileExtension(fileName));
        String mediaType = mediaTypeService.setMediaType(mediaDto);
        mediaDto.setMediaType(mediaType);
        Media mediaModel = mediaMapper.convertToModel(mediaDto);
        mediaModel.setCreationDate(LocalDateTime.now());
        return mediaModel;
    }

    public String fileIDWithExtension(MediaDto mediaDto) {
        List<String> name = Arrays.asList(mediaDto.getFileId(), mediaDto.getExtension()/* media extension*/);
        return String.join(".", name);
    }

    private String getFileExtension(String fileName) {
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        else return "";
    }

    private String generateFileId() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}
