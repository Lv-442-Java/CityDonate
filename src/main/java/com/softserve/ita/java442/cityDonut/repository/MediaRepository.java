package com.softserve.ita.java442.cityDonut.repository;

import com.softserve.ita.java442.cityDonut.model.Media;
import com.softserve.ita.java442.cityDonut.model.MediaType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MediaRepository extends JpaRepository<Media, Long> {
    List<Media> getPhotosByGalleryIdAndMediaType(long galleryId, MediaType mediaType);
    Media getByFileId(String fileId);
    List<Media> getFilesByGalleryId(long galleryId);
    Media getFileByFileIdAndGalleryId(String fileId, long galleryId);
}