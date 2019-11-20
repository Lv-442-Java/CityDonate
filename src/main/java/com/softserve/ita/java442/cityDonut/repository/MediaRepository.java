package com.softserve.ita.java442.cityDonut.repository;

import com.softserve.ita.java442.cityDonut.model.Media;
import com.softserve.ita.java442.cityDonut.model.MediaType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MediaRepository extends JpaRepository<Media, Long> {
    List<Media> getPhotosByProjectIdAndMediaTypeAndStoryBoard_IdNull(long projectId, MediaType mediaType);
    Media findByFileId(String fileId);
    Media findByNameAndProjectId(String name, long projectId);
    Media findByName(String name);
    List<Media> getFilesByProjectId(long projectId);
}
