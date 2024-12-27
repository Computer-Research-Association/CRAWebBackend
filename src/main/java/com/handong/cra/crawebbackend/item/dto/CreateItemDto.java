package com.handong.cra.crawebbackend.item.dto;

import com.handong.cra.crawebbackend.item.dto.request.ReqCreateItemDto;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class CreateItemDto {
    private String name;
    private String description;
    private String imageUrl;

    private Long id = null;
    private LocalDateTime createdAt = null;

    public CreateItemDto(ReqCreateItemDto reqCreateItemDto) {
        this.name = reqCreateItemDto.getName();
        this.description = reqCreateItemDto.getDescription();
        this.imageUrl = reqCreateItemDto.getImageUrl();
    }

    public static CreateItemDto from(ReqCreateItemDto reqCreateItemDto) {
        return new CreateItemDto(reqCreateItemDto);
    }
}
