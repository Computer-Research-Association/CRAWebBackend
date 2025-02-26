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
    private Long userId;
    private String className;
    private String professor;
    private Boolean deleted;

    public UpdateHavrutaDto(Long havrutaId, Long userId, ReqUpdateHavrutaDto reqUpdateHavrutaDto) {
        this.id = havrutaId;
        this.userId = userId;
        this.className = reqUpdateHavrutaDto.getClassName();
        this.professor = reqUpdateHavrutaDto.getProfessor();
    }

    public UpdateHavrutaDto(Havruta havruta) {
        this.id = havruta.getId();
        this.className = havruta.getClassname();
        this.professor = havruta.getProfessor();
        this.deleted = havruta.getDeleted();
    }

    public UpdateHavrutaDto(Long havrutaId, Long userId, Boolean deleted) {
        this.id = havrutaId;
        this.userId = userId;
        this.deleted = deleted;
    }

    public static UpdateHavrutaDto of(Long havrutaId, Long userId, ReqUpdateHavrutaDto reqUpdateHavrutaDto) {
        return new UpdateHavrutaDto(havrutaId, userId, reqUpdateHavrutaDto);
    }

    public static UpdateHavrutaDto of(Long havrutaId, Long userId, Boolean deleted) {
        return new UpdateHavrutaDto(havrutaId, userId, deleted);
    }

    public static UpdateHavrutaDto from(Havruta havruta) {
        return new UpdateHavrutaDto(havruta);
    }
}
