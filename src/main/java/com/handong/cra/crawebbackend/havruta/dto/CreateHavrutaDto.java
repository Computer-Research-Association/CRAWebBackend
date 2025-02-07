package com.handong.cra.crawebbackend.havruta.dto;

import com.handong.cra.crawebbackend.havruta.domain.Havruta;
import com.handong.cra.crawebbackend.havruta.dto.request.ReqCreateHavrutaDto;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CreateHavrutaDto {

    private Long id;
    private String className;
    private String professor;
    private LocalDateTime createdAt;

    public CreateHavrutaDto(ReqCreateHavrutaDto reqCreateHavrutaDto) {
        this.className = reqCreateHavrutaDto.getClassName();
        this.professor = reqCreateHavrutaDto.getProfessor();
    }

    public CreateHavrutaDto(Havruta havruta) {
        this.id = havruta.getId();
        this.className = havruta.getClassname();
        this.professor = havruta.getProfessor();
        this.createdAt = havruta.getCreatedAt();
    }

    public static CreateHavrutaDto from(ReqCreateHavrutaDto reqCreateHavrutaDto) {
        return new CreateHavrutaDto(reqCreateHavrutaDto);
    }

    public static CreateHavrutaDto from(Havruta havruta) {
        return new CreateHavrutaDto(havruta);
    }
}
