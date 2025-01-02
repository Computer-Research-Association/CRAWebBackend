package com.handong.cra.crawebbackend.havruta.dto;

import com.handong.cra.crawebbackend.havruta.dto.request.ReqCreateHavrutaDto;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class CreateHavrutaDto {
    private Long id = null;
    private String className;
    private String professor;
    private LocalDateTime createdAt = null;


    public CreateHavrutaDto(ReqCreateHavrutaDto reqCreateHavrutaDto) {
        this.className = reqCreateHavrutaDto.getClassName();
        this.professor = reqCreateHavrutaDto.getProfessor();
    }

}
