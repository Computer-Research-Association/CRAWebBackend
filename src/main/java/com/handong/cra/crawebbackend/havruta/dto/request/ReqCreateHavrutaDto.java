package com.handong.cra.crawebbackend.havruta.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ReqCreateHavrutaDto {

    private String className;
    private String professor;
}
