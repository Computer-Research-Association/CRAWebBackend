package com.handong.cra.crawebbackend.item.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ReqUpdateItemDto {
    private String name;
    private String imageUrl;
}
