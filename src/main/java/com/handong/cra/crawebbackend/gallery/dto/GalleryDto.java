package com.handong.cra.crawebbackend.gallery.dto;

import com.handong.cra.crawebbackend.gallery.domain.Gallery;
import com.handong.cra.crawebbackend.gallery.dto.request.ReqGalleryDto;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GalleryDto {
    private Long id;
    private String imgUrl;

    public GalleryDto(ReqGalleryDto reqGalleryDto) {
        this.imgUrl = reqGalleryDto.getImgUrl();
    }

    public GalleryDto(Gallery gallery) {
        this.id = gallery.getId();
        this.imgUrl = gallery.getImgUrl();
    }

    public static GalleryDto from(ReqGalleryDto reqGalleryDto) {
        return new GalleryDto(reqGalleryDto);
    }

    public static GalleryDto from(Gallery gallery) {
        return new GalleryDto(gallery);
    }
}
