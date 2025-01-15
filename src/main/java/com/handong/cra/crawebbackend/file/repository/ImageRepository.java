package com.handong.cra.crawebbackend.file.repository;

import com.handong.cra.crawebbackend.file.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {
    public List<Image> findAllIsUsingFalse();
}
