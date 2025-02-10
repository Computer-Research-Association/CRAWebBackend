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
    private Long userId;
    private String name;
    private String description;
    private ItemCategory itemCategory;
    private String imageUrl;
    private Boolean isBorrowed;
    private Boolean deleted;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public UpdateItemDto(Long userId, Long itemId, ReqUpdateItemDto reqUpdateItemDto) {
        this.id = itemId;
        this.name = reqUpdateItemDto.getName();
        this.userId = userId;
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

    public UpdateItemDto(Long itemId, Long userId, Boolean deleted) {
        this.id = itemId;
        this.userId = userId;
        this.deleted = deleted;
    }

    public UpdateItemDto(Long itemId, Long userId) {
        this.id = itemId;
        this.userId = userId;
    }


    public static UpdateItemDto of(Long userId, Long itemId, ReqUpdateItemDto reqUpdateItemDto) {
        return new UpdateItemDto(userId, itemId, reqUpdateItemDto);
    }

    // 삭제용
    public static UpdateItemDto of(Long itemId, Long userId) {
        return new UpdateItemDto(itemId, userId);
    }

    // 대여용
    public static UpdateItemDto of(Long itemId, Long userId, Boolean isBorrowed) {
        return new UpdateItemDto(itemId, userId, isBorrowed);
    }

    public static UpdateItemDto from(Item item) {
        return new UpdateItemDto(item);
    }
}
