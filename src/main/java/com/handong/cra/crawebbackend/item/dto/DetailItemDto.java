package com.handong.cra.crawebbackend.item.dto;

import com.handong.cra.crawebbackend.item.domain.Item;
import com.handong.cra.crawebbackend.item.domain.ItemCategory;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor (access = AccessLevel.PROTECTED)
public class DetailItemDto {
    private Long id;
    private String name;
    private String description;
    private ItemCategory itemCategory;
    private String imageUrl;
    private Boolean isBorrowed;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public DetailItemDto(Item item) {
        this.id = item.getId();
        this.name = item.getName();
        this.description = item.getDescription();
        this.itemCategory = item.getItemCategory();
        this.imageUrl = item.getImageUrl();
        this.isBorrowed = item.getIsBorrowed();
        this.createdAt = item.getCreatedAt();
        this.updatedAt = item.getUpdatedAt();
    }

    public static DetailItemDto from(Item item) {
        return new DetailItemDto(item);
    }

}
