package com.handong.cra.crawebbackend.havruta.dto.response;

import com.handong.cra.crawebbackend.havruta.dto.DetailHavrutaDto;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Setter
@Getter
@Builder
public class ResDetailHavrutaDto {
    private Long id;
    private String className;
    private List<Long> boards;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ResDetailHavrutaDto(DetailHavrutaDto detailHavrutaDto) {

    }

}
