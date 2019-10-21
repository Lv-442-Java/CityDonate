package com.softserve.ita.java442.cityDonut.service.impl;

import com.softserve.ita.java442.cityDonut.repository.MediaTypeRepository;
import com.softserve.ita.java442.cityDonut.service.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MediaTypeServiceImpl implements MediaService {

    @Autowired
    MediaTypeRepository mediaTypeRepository;
}
