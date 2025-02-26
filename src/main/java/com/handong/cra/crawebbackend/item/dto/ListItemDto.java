package com.handong.cra.crawebbackend.item.dto;

import com.handong.cra.crawebbackend.item.domain.Item;
import com.handong.cra.crawebbackend.item.domain.ItemCategory;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ListItemDto {
    private Long id;
    private String name;
    private String description;
    private String imageUrl;
    private Boolean isBorrowed;
    private ItemCategory itemCategory;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ListItemDto(Item item) {
        this.id = item.getId();
        this.name = item.getName();
        this.description = item.getDescription();
        this.imageUrl = item.getImageUrl();
        this.isBorrowed = item.getIsBorrowed();
        this.itemCategory = item.getItemCategory();
        this.createdAt = item.getCreatedAt();
        this.updatedAt = item.getUpdatedAt();
    }

    public static ListItemDto from(Item item) {
        return new ListItemDto(item);
    }

}
