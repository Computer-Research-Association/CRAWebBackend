package com.handong.cra.crawebbackend.gallery.domain;


import com.handong.cra.crawebbackend.common.domain.BaseEntity;
import com.handong.cra.crawebbackend.gallery.dto.GalleryDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Gallery extends BaseEntity {

    @Setter
    @Column(nullable = false, name = "img_url")
    private String imgUrl;

    public Gallery(GalleryDto galleryDto) {
        this.imgUrl = galleryDto.getImgUrl();
    }

    public static Gallery from(GalleryDto galleryDto) {
        return new Gallery(galleryDto);
    }

}