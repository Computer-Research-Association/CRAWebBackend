package com.handong.cra.crawebbackend.havruta.dto;

import com.handong.cra.crawebbackend.havruta.domain.Havruta;
import com.handong.cra.crawebbackend.havruta.dto.request.ReqUpdateHavrutaDto;
import lombok.*;

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

    public static UpdateHavrutaDto of(Long id, ReqUpdateHavrutaDto reqUpdateHavrutaDto) {
        return new UpdateHavrutaDto(id, reqUpdateHavrutaDto);
    }

    public static UpdateHavrutaDto from(Havruta havruta) {
        return new UpdateHavrutaDto(havruta);
    }
}
