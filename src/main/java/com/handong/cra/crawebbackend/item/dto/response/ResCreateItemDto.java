package com.handong.cra.crawebbackend.item.dto.response;

import com.handong.cra.crawebbackend.item.dto.CreateItemDto;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ResCreateItemDto {
    private Long id;
    private String name;
    private String description;
    private String imageUrl;
    private Integer itemCategory;
    private Boolean isBorrowed = false;
    private LocalDateTime createdAt;

    public ResCreateItemDto(CreateItemDto createItemDto) {
        this.id = createItemDto.getId();
        this.name = createItemDto.getName();
        this.description = createItemDto.getDescription();
        this.imageUrl = createItemDto.getImageUrl();
        this.itemCategory = createItemDto.getItemCategory().ordinal();
        this.isBorrowed = false;
        this.createdAt = createItemDto.getCreatedAt();
    }

    public static ResCreateItemDto from(CreateItemDto createItemDto) {
        return new ResCreateItemDto(createItemDto);
    }
}
