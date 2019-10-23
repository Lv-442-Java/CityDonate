package com.softserve.ita.java442.cityDonut.service.impl;

import com.softserve.ita.java442.cityDonut.model.Media;
import com.softserve.ita.java442.cityDonut.repository.MediaRepository;
import com.softserve.ita.java442.cityDonut.service.MediaService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class MediaServiceImpl implements MediaService {
    @Autowired
    MediaRepository mediaRepository;

        @Override
        public Optional<Media> findByName(String name) {
           return mediaRepository.findByName(name);
        }
}
