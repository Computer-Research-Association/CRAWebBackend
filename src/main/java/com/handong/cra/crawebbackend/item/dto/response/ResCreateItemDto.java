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
    private LocalDateTime createdAt;

    public static ResCreateItemDto from(CreateItemDto createItemDto) {
        return ResCreateItemDto.builder()
                .id(createItemDto.getId())
                .name(createItemDto.getName())
                .description(createItemDto.getDescription())
                .imageUrl(createItemDto.getImageUrl())
                .createdAt(createItemDto.getCreatedAt())
                .build();
    }
}
