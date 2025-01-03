package com.handong.cra.crawebbackend.havruta.dto.response;

import com.handong.cra.crawebbackend.havruta.dto.UpdateHavrutaDto;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ResUpdateHavrutaDto {

    private Long id;
    private String className;
    private String professor;
    private Boolean deleted;

    public ResUpdateHavrutaDto(UpdateHavrutaDto updateHavrutaDto) {
        this.id = updateHavrutaDto.getId();
        this.className = updateHavrutaDto.getClassName();
        this.professor = updateHavrutaDto.getProfessor();
        this.deleted = updateHavrutaDto.getDeleted();
    }

    public static ResUpdateHavrutaDto from(UpdateHavrutaDto updateHavrutaDto) {
        return new ResUpdateHavrutaDto(updateHavrutaDto);
    }
}


