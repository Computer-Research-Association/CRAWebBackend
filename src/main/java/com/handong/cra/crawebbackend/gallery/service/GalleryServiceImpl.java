package com.handong.cra.crawebbackend.gallery.service;

import com.handong.cra.crawebbackend.board.domain.BoardOrderBy;
import com.handong.cra.crawebbackend.exception.gallery.GalleryNotFoundException;
import com.handong.cra.crawebbackend.file.domain.S3ImageCategory;
import com.handong.cra.crawebbackend.file.service.S3ImageService;
import com.handong.cra.crawebbackend.gallery.domain.Gallery;
import com.handong.cra.crawebbackend.gallery.dto.GalleryDto;
import com.handong.cra.crawebbackend.gallery.repository.GalleryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GalleryServiceImpl implements GalleryService {

    private final GalleryRepository galleryRepository;
    private final S3ImageService s3ImageService;

    @Override
    @Transactional
    public Boolean deleteById(Long id) {
        Gallery gallery = galleryRepository.findById(id).orElseThrow(GalleryNotFoundException::new);
        gallery.delete();
        gallery.setImgUrl(s3ImageService.transferImage(gallery.getImgUrl(), S3ImageCategory.DELETED));
        return true;
    }

    @Override
    @Transactional
    public GalleryDto createGallery(GalleryDto galleryDto) {

        // img logic
        String imgUrl = s3ImageService.transferImage(galleryDto.getImgUrl(), S3ImageCategory.GALLERY);
        galleryDto.setImgUrl(imgUrl);

        Gallery gallery = Gallery.from(galleryDto);
        gallery = galleryRepository.save(gallery);

        return GalleryDto.from(gallery);
    }

    @Override
    @Transactional
    public GalleryDto updateGallery(Long id, GalleryDto galleryDto) {
        Gallery gallery = galleryRepository.findById(id).orElseThrow(GalleryNotFoundException::new);
        s3ImageService.transferImage(gallery.getImgUrl(), S3ImageCategory.DELETED);
        String url = s3ImageService.transferImage(galleryDto.getImgUrl(), S3ImageCategory.GALLERY);
        gallery.setImgUrl(url);
        galleryDto.setImgUrl(url);

        return galleryDto;
    }

    @Override
    public List<GalleryDto> getGalleryList() {
        List<Gallery> galleries = galleryRepository.findAllByDeletedIsFalse();
        return galleries.stream().map(GalleryDto::from).toList();
    }

    @Override
    public List<GalleryDto> getPaginationGallery(Long page, Integer perPage, BoardOrderBy value, Boolean isASC) {

        Sort sort = Sort.by("createdAt");

        sort = (isASC) ? sort.ascending() : sort.descending();

        Pageable pageable = PageRequest.of(Math.toIntExact(page), perPage, sort);
        Page<Gallery> galleries = galleryRepository.findAllByDeletedIsFalse(pageable);

        return galleries.stream().map(GalleryDto::from).toList();
    }

    @Override
    public GalleryDto getDetailGalleryById(Long id) {
        Gallery gallery = galleryRepository.findById(id).orElseThrow(GalleryNotFoundException::new);
        if (gallery.getDeleted()) throw new GalleryNotFoundException();
        return GalleryDto.from(gallery);
    }
}
