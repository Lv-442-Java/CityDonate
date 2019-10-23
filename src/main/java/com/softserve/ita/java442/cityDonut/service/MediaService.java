package com.softserve.ita.java442.cityDonut.service;

import com.softserve.ita.java442.cityDonut.model.Media;
import java.util.Optional;

public interface MediaService {
    Optional<Media> findByName(String name);
}
