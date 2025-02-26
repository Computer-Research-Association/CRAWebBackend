package com.handong.cra.crawebbackend.item.dto;

import com.handong.cra.crawebbackend.item.domain.Item;
import com.handong.cra.crawebbackend.item.domain.ItemCategory;
import com.handong.cra.crawebbackend.item.dto.request.ReqCreateItemDto;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class CreateItemDto {
    private Long id;
    private Long userId;
    private String name;
    private String description;
    private ItemCategory itemCategory;
    private String imageUrl;
    private LocalDateTime createdAt = null;


    public CreateItemDto(Long userId, ReqCreateItemDto reqCreateItemDto) {
        this.name = reqCreateItemDto.getName();
        this.userId = userId;
        this.description = reqCreateItemDto.getDescription();
        this.itemCategory = ItemCategory.values()[reqCreateItemDto.getItemCategory()];
        this.imageUrl = reqCreateItemDto.getImageUrl();
    }

    public CreateItemDto(Item item){
        this.id = item.getId();
        this.name = item.getName();
        this.description = item.getDescription();
        this.itemCategory = item.getItemCategory();
        this.imageUrl = item.getImageUrl();
        this.createdAt = item.getCreatedAt();
    }

    public static CreateItemDto  of(Long userId, ReqCreateItemDto reqCreateItemDto) {
        return new CreateItemDto(userId, reqCreateItemDto);
    }

    public static CreateItemDto from(Item item) {
        return new CreateItemDto(item);
    }
}
