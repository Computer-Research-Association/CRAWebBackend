package com.handong.cra.crawebbackend.havruta.dto.response;

import com.handong.cra.crawebbackend.havruta.dto.CreateHavrutaDto;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ResCreateHavrutaDto {

    private Long Id;
    private String className;
    private String professor;
    private LocalDateTime createdAt;

    public ResCreateHavrutaDto(CreateHavrutaDto createHavrutaDto) {
        this.Id = createHavrutaDto.getId();
        this.className = createHavrutaDto.getClassName();
        this.professor = createHavrutaDto.getProfessor();
        this.createdAt = createHavrutaDto.getCreatedAt();
    }

    public static ResCreateHavrutaDto from(CreateHavrutaDto createHavrutaDto) {
        return new ResCreateHavrutaDto(createHavrutaDto);
    }
}


