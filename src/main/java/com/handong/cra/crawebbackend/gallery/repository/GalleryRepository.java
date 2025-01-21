package com.handong.cra.crawebbackend.gallery.repository;

import com.handong.cra.crawebbackend.gallery.domain.Gallery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GalleryRepository extends JpaRepository<Gallery, Long> {
    public List<Gallery> findAllByDeletedIsFalse();
    public Page<Gallery> findAllByDeletedIsFalse(Pageable pageable);
}
