package com.handong.cra.crawebbackend.havruta.dto.request;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Setter
@Getter
@Builder
public class ReqCreateHavrutaDto {
    //--------------------------------------
    // TODO : security 처리 필요
    private Long userId;
    //--------------------------------------
    private String className;
}
