package com.handong.cra.crawebbackend.havruta.dto;

import com.handong.cra.crawebbackend.havruta.domain.Havruta;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ListHavrutaDto {
    private Long id;
    private String className;
    private String professor;

    private LocalDateTime createdAt;

    public ListHavrutaDto(Havruta havruta) {
        this.id = havruta.getId();
        this.className = havruta.getClassName();
        this.professor = havruta.getProfessor();
        this.createdAt = havruta.getCreatedAt();
    }
}
