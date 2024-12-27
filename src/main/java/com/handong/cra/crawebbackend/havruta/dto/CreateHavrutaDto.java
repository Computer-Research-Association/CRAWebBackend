package com.handong.cra.crawebbackend.havruta.dto;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Setter
@Getter
@Builder
public class CreateHavrutaDto {
    private String className;
}