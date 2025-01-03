package com.handong.cra.crawebbackend.havruta.dto.response;

import com.handong.cra.crawebbackend.havruta.domain.Havruta;
import com.handong.cra.crawebbackend.havruta.dto.DetailHavrutaDto;
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

    public static ResDetailHavrutaDto from(DetailHavrutaDto detailHavrutaDto) {
        if(detailHavrutaDto == null) return null;
        return ResDetailHavrutaDto.builder()
                .id(detailHavrutaDto.getId())
                .className(detailHavrutaDto.getClassName())
                .professor(detailHavrutaDto.getProfessor())
                .createdAt(detailHavrutaDto.getCreatedAt())
                .updatedAt(detailHavrutaDto.getUpdatedAt())
                .build();
    }
}
