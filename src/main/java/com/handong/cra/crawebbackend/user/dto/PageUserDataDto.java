package com.handong.cra.crawebbackend.user.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class PageUserDataDto {
    private Long page;
    private Integer perPage;
}
