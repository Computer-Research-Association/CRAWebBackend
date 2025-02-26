package com.handong.cra.crawebbackend.havruta.dto;

import com.handong.cra.crawebbackend.havruta.domain.Havruta;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class HavrutaDto {
    private Long id;
    private String classname;
    private String professor;

    public HavrutaDto(Havruta havruta) {
        this.id = havruta.getId();
        this.classname = havruta.getClassname();
        this.professor = havruta.getProfessor();
    }

    public static HavrutaDto from(Havruta havruta) {
        return new HavrutaDto(havruta);
    }
}
