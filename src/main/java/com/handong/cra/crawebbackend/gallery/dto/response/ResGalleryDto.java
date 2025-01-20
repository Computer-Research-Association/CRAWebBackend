package com.handong.cra.crawebbackend.gallery.dto.response;

import com.handong.cra.crawebbackend.gallery.dto.GalleryDto;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResGalleryDto {
    private Long id;
    private String imgUrl;

    public ResGalleryDto(GalleryDto galleryDto){
        this.id = galleryDto.getId();
        this.imgUrl = galleryDto.getImgUrl();
    }

    public static ResGalleryDto from(GalleryDto galleryDto){
        return new ResGalleryDto(galleryDto);
    }
}
