package com.handong.cra.crawebbackend.item.dto;

import com.handong.cra.crawebbackend.item.domain.Item;
import com.handong.cra.crawebbackend.item.domain.ItemCategory;
import com.handong.cra.crawebbackend.user.dto.UserDetailDto;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DetailItemDto {
    private Long id;
    private String name;
    private String description;
    private ItemCategory itemCategory;
    private String imageUrl;
    private Boolean isBorrowed;
    private UserDetailDto userDetailDto;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public DetailItemDto(Item item) {
        this.id = item.getId();
        this.name = item.getName();
        this.description = item.getDescription();
        this.itemCategory = item.getItemCategory();
        this.imageUrl = item.getImageUrl();
        this.isBorrowed = item.getIsBorrowed();
        if (item.getBorrowerUser() !=null)
            this.userDetailDto = UserDetailDto.from(item.getBorrowerUser());
        this.createdAt = item.getCreatedAt();
        this.updatedAt = item.getUpdatedAt();
    }

    public static DetailItemDto from(Item item) {
        return new DetailItemDto(item);
    }

}
