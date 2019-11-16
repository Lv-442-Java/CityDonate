package com.softserve.ita.java442.cityDonut.repository;

import com.softserve.ita.java442.cityDonut.model.Media;
import com.softserve.ita.java442.cityDonut.model.MediaType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MediaRepository extends JpaRepository<Media, Long> {
    List<Media> getPhotosByProjectIdAndMediaType(long projectId, MediaType mediaType);
    Media findByNameAndProjectId(String name, long projectId);
    List<Media> getFilesByProjectId(long projectId);
}
