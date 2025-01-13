package com.handong.cra.crawebbackend.item.dto.response;

import com.handong.cra.crawebbackend.item.dto.UpdateItemDto;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResUpdateItemDto {
    private Long id;
    private String name;
    private String description;
    private Integer itemCategory;
    private String imageUrl;
    private Boolean isBorrowed;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ResUpdateItemDto(UpdateItemDto updateItemDto) {
        this.id = updateItemDto.getId();
        this.name = updateItemDto.getName();
        this.description = updateItemDto.getDescription();
        this.itemCategory = updateItemDto.getItemCategory().ordinal();
        this.imageUrl = updateItemDto.getImageUrl();
        this.isBorrowed = updateItemDto.getIsBorrowed();
        this.createdAt = updateItemDto.getCreatedAt();
        this.updatedAt = updateItemDto.getUpdatedAt();
    }

    public static ResUpdateItemDto from(UpdateItemDto updateItemDto) {
        return new ResUpdateItemDto(updateItemDto);
    }

}

