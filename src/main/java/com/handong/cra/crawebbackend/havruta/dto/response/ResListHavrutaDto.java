package com.handong.cra.crawebbackend.havruta.dto.response;

import com.handong.cra.crawebbackend.havruta.dto.ListHavrutaDto;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ResListHavrutaDto {
    private Long id;
    private String className;
    private String professor;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ResListHavrutaDto(ListHavrutaDto listHavrutaDto) {
        this.id = listHavrutaDto.getId();
        this.className = listHavrutaDto.getClassName();
        this.professor = listHavrutaDto.getProfessor();
        this.createdAt = listHavrutaDto.getCreatedAt();
        this.updatedAt = listHavrutaDto.getUpdatedAt();
    }

    public static ResListHavrutaDto from(ListHavrutaDto listHavrutaDto){
        return new ResListHavrutaDto(listHavrutaDto);
    }
}
