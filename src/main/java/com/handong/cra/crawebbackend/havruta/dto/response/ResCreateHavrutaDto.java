package com.handong.cra.crawebbackend.havruta.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ResCreateHavrutaDto {
    private Long id;
    private String className;
    private String professor;
    private String createdAt;
}
