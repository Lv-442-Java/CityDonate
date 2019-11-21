package com.softserve.ita.java442.cityDonut.repository;

import com.softserve.ita.java442.cityDonut.model.Gallery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GalleryRepository extends JpaRepository<Gallery, Long> {

}
