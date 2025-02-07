package com.handong.cra.crawebbackend.havruta.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HavrutaDto {
    private Long id;
    private String classname;
    private String professor;
}
