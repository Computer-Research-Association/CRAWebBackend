package com.handong.cra.crawebbackend.item.dto.response;

import com.handong.cra.crawebbackend.item.dto.ListItemDto;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ResListItemDto {
    private Long id;
    private String name;
    private String description;
    private String imageUrl;
    private Boolean isBorrowed;
    private Integer itemCategory;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ResListItemDto(ListItemDto listItemDto) {
        this.id = listItemDto.getId();
        this.name = listItemDto.getName();
        this.description = listItemDto.getDescription();
        this.itemCategory = listItemDto.getItemCategory().ordinal();
        this.imageUrl = listItemDto.getImageUrl();
        this.isBorrowed = listItemDto.getIsBorrowed();
        this.createdAt = listItemDto.getCreatedAt();
        this.updatedAt = listItemDto.getUpdatedAt();
    }

    public static ResListItemDto from(ListItemDto listItemDto) {
        return new ResListItemDto(listItemDto);
    }
}
