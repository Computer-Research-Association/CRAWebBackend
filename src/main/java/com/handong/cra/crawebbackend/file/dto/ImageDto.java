package com.handong.cra.crawebbackend.file.dto;

import com.handong.cra.crawebbackend.file.domain.ImageCategory;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ImageDto {
    private Long id;
    private String imgUrl;
    private String key;
    private ImageCategory imageCategory;
    private LocalDateTime createdAt;
    private Boolean isUsing;
}
