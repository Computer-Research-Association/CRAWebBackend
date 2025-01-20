package com.handong.cra.crawebbackend.gallery.repository;

import com.handong.cra.crawebbackend.gallery.domain.Gallery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GalleryRepository extends JpaRepository<Gallery, Long> {
}
