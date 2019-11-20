package com.softserve.ita.java442.cityDonut.service;

import com.softserve.ita.java442.cityDonut.dto.media.MediaDto;

public interface MediaService {

    MediaDto saveMedia(MediaDto mediaDto, String fileName);
}
