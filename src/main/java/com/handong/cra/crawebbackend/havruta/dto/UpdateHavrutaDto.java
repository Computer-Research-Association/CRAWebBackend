package com.handong.cra.crawebbackend.havruta.dto;

import com.handong.cra.crawebbackend.havruta.dto.request.ReqUpdateHavrutaDto;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Setter
@Getter
@Builder
public class UpdateHavrutaDto {
    private Long id;
    private String className;
    private List<Long> boards;
    private LocalDateTime createdAt = null;
    private LocalDateTime updatedAt = null;

    public UpdateHavrutaDto(Long id, ReqUpdateHavrutaDto reqUpdateHavrutaDto) {
        this.id = id;
        this.className = reqUpdateHavrutaDto.getClassName();
        this.boards = reqUpdateHavrutaDto.getBoards();
    }

}
