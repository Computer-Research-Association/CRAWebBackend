package com.handong.cra.crawebbackend.havruta.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ReqUpdateHavrutaDto {
    private String className;
    private String professor;
    private Boolean deleted;
    // 애매하네요
}
