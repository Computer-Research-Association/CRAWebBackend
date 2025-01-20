package com.handong.cra.crawebbackend.gallery.service;

import com.handong.cra.crawebbackend.board.domain.BoardOrderBy;
import com.handong.cra.crawebbackend.gallery.dto.GalleryDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GalleryService {
    public Boolean deleteById(Long id);
    public GalleryDto createGallery(GalleryDto galleryDto);
    public GalleryDto updateGallery(Long id, GalleryDto from);
    public List<GalleryDto> getGalleryList();
    public List<GalleryDto> getPaginationGallery(Long page, Integer perPage, BoardOrderBy value, Boolean isASC);
    public GalleryDto getDetailGalleryById(Long id);
}
