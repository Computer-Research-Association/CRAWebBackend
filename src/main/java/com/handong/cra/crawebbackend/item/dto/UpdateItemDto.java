package com.handong.cra.crawebbackend.item.dto;


import com.handong.cra.crawebbackend.item.domain.Item;
import com.handong.cra.crawebbackend.item.domain.ItemCategory;
import com.handong.cra.crawebbackend.item.dto.request.ReqUpdateItemDto;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdateItemDto {
    private Long id;
    private String name;
    private String description;
    private ItemCategory itemCategory;
    private String imageUrl;
    private Boolean isBorrowed;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public UpdateItemDto(ReqUpdateItemDto reqUpdateItemDto) {
        this.name = reqUpdateItemDto.getName();
        this.description = reqUpdateItemDto.getDescription();
        this.itemCategory = ItemCategory.values()[reqUpdateItemDto.getItemCategory()];
        this.imageUrl = reqUpdateItemDto.getImageUrl();
        this.isBorrowed = reqUpdateItemDto.getIsBorrowed();
    }

    public UpdateItemDto(Item item) {
        this.id = item.getId();
        this.name = item.getName();
        this.description = item.getDescription();
        this.itemCategory = item.getItemCategory();
        this.imageUrl = item.getImageUrl();
        this.isBorrowed = item.getIsBorrowed();
        this.createdAt = item.getCreatedAt();
        this.updatedAt = item.getUpdatedAt();
    }


    public static UpdateItemDto from(ReqUpdateItemDto reqUpdateItemDto) {
        return new UpdateItemDto(reqUpdateItemDto);
    }
    public static UpdateItemDto from(Item item) {
        return new UpdateItemDto(item);
    }
}
