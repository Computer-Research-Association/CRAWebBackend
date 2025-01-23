package com.handong.cra.crawebbackend.item.domain;

import com.handong.cra.crawebbackend.common.domain.BaseEntity;
import com.handong.cra.crawebbackend.item.dto.CreateItemDto;
import com.handong.cra.crawebbackend.item.dto.UpdateItemDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item extends BaseEntity {
    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 2048, nullable = false)
    private String description;

    @Setter
    @Column(name = "imgae_url", length = 256)
    private String imageUrl;

    @Setter
    @Column(name = "is_broowed", nullable = false)
    private Boolean isBorrowed;

    @Column(nullable = false)
    private ItemCategory itemCategory; // 0 = book 1 = item

    public Item(CreateItemDto createItemDto) {
        this.name = createItemDto.getName();
        this.description = createItemDto.getDescription();
        this.itemCategory = createItemDto.getItemCategory();
        this.imageUrl = createItemDto.getImageUrl();
        this.isBorrowed = false;
    }

    public static Item from(CreateItemDto createItemDto) {
        return new Item(createItemDto);
    }

    public Item update(UpdateItemDto updateItemDto) {
        this.name = updateItemDto.getName();
        this.description = updateItemDto.getDescription();
        this.imageUrl = updateItemDto.getImageUrl();
        this.isBorrowed = updateItemDto.getIsBorrowed();
        this.itemCategory = updateItemDto.getItemCategory();

        return this;
    }

}
