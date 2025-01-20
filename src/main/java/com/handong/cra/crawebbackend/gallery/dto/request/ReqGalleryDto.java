package com.handong.cra.crawebbackend.gallery.dto.request;

import com.handong.cra.crawebbackend.gallery.dto.GalleryDto;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReqGalleryDto {
    private String imgUrl;
}
