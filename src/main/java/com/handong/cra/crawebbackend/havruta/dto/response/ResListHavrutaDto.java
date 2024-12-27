package com.handong.cra.crawebbackend.havruta.dto.response;


import com.handong.cra.crawebbackend.havruta.dto.ListHavrutaDto;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ResListHavrutaDto {

    private Long id;
    private String className;

    public ResListHavrutaDto(ListHavrutaDto listHavrutaDto) {



    }
}
