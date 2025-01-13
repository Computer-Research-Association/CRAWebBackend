package com.handong.cra.crawebbackend.item.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ReqCreateItemDto {
    private String name;
    private String description;
    private Integer itemCategory;
    private String imageUrl;
}
