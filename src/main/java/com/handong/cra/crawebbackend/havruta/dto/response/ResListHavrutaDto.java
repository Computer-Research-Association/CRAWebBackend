package com.handong.cra.crawebbackend.havruta.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ResListHavrutaDto {
    private Long id;
    private String className;
    private String professor;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
