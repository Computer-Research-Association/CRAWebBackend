package com.handong.cra.crawebbackend.havruta.dto.request;

import lombok.*;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Setter
@Getter
@Builder
public class ReqUpdateHavrutaDto {
    //--------------------------------------
    // TODO : security 처리 필요
    private Long userId;
    //--------------------------------------

    private String className;
    private List<Long> boards;
    private Boolean deleted;
}
