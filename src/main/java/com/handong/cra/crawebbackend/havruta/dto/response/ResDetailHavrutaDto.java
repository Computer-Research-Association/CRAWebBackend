package com.handong.cra.crawebbackend.havruta.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ResDetailHavrutaDto {
    private Long id;
    private String className;
    private String professor;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
