package com.handong.cra.crawebbackend.havruta.dto.response;

import com.handong.cra.crawebbackend.havruta.dto.CreateHavrutaDto;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Setter
@Getter
@Builder
public class ResCreateHavrutaDto {
    private Long id;
    private String className;
    private LocalDateTime createdAt;


    public ResCreateHavrutaDto(CreateHavrutaDto createhavrutaDto) {




    }
}
