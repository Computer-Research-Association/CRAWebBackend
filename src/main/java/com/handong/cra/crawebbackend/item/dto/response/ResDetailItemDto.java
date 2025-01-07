package com.handong.cra.crawebbackend.item.dto.response;

import com.handong.cra.crawebbackend.item.dto.DetailItemDto;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ResDetailItemDto {
    private Long id;
    private String name;
    private String description;
    private Integer itemCategory;
    private String imageUrl;
    private Boolean isBorrowed;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ResDetailItemDto(DetailItemDto detailItemDto) {
        this.id = detailItemDto.getId();
        this.name = detailItemDto.getName();
        this.description = detailItemDto.getDescription();
        this.itemCategory = detailItemDto.getItemCategory().ordinal();
        this.imageUrl = detailItemDto.getImageUrl();
        this.isBorrowed = detailItemDto.getIsBorrowed();
        this.createdAt = detailItemDto.getCreatedAt();
        this.updatedAt = detailItemDto.getUpdatedAt();
    }

    public static ResDetailItemDto from(DetailItemDto detailItemDto) {
        return new ResDetailItemDto(detailItemDto);
    }


}
