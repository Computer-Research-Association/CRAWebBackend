package com.handong.cra.crawebbackend.havruta.dto;

import com.handong.cra.crawebbackend.havruta.domain.Havruta;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class DetailHavrutaDto {
    private Long id;
    private String className;
    private String professor;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public DetailHavrutaDto(Havruta havruta) {
        this.id = havruta.getId();
        this.className = havruta.getClassName();
        this.professor = havruta.getProfessor();
        this.createdAt = havruta.getCreatedAt();
        this.updatedAt = havruta.getUpdatedAt();
    }

    public static DetailHavrutaDto from(Havruta havruta){
        return new DetailHavrutaDto(havruta);
    }
}
