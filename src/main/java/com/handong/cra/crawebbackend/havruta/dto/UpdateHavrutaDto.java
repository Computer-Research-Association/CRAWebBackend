package com.handong.cra.crawebbackend.havruta.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Setter
@Getter
@Builder
public class UpdateHavrutaDto {
    private Long id;
    private String className;
    private List<Long> boards;

}
