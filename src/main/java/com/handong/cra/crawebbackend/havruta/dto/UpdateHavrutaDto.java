package com.handong.cra.crawebbackend.havruta.dto;

import com.handong.cra.crawebbackend.havruta.domain.Havruta;
import com.handong.cra.crawebbackend.havruta.dto.request.ReqUpdateHavrutaDto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class UpdateHavrutaDto {
    private Long id;
    private String className;
    private String professor;
    private Boolean deleted;

    public UpdateHavrutaDto(Long id, ReqUpdateHavrutaDto reqUpdateHavrutaDto) {
        this.id = id;
        this.className = reqUpdateHavrutaDto.getClassName();
        this.professor = reqUpdateHavrutaDto.getProfessor();
        this.deleted = reqUpdateHavrutaDto.getDeleted();
    }

    public UpdateHavrutaDto(Havruta havruta) {
        this.id = havruta.getId();
        this.className = havruta.getClassName();
        this.professor = havruta.getProfessor();
        this.deleted = havruta.getDeleted();
    }
}
