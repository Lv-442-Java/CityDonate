package com.softserve.ita.java442.cityDonut.repository;

import com.softserve.ita.java442.cityDonut.model.Media;
import com.softserve.ita.java442.cityDonut.model.MediaType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MediaRepository extends JpaRepository<Media, Long> {
    List<Media> getPhotosByGalleryIdAndMediaType(long galleryId, MediaType mediaType);
    Media findByFileId(String fileId);
    Media findByNameAndGalleryId(String name, long galleryId);
    Media findByName(String name);
    List<Media> getFilesByGalleryId(long galleryId);
}
