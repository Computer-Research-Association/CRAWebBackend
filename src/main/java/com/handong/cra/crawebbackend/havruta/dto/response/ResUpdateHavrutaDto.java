package com.handong.cra.crawebbackend.havruta.dto.response;


import com.handong.cra.crawebbackend.havruta.dto.UpdateHavrutaDto;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class ResUpdateHavrutaDto {
    private Long id;
    private String className;
    private List<Long> boards;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ResUpdateHavrutaDto(UpdateHavrutaDto updateHavrutaDto) {
        this.id = updateHavrutaDto.getId();
        this.className = updateHavrutaDto.getClassName();
        this.boards = updateHavrutaDto.getBoards();
        this.createdAt = updateHavrutaDto.getCreatedAt();
        this.updatedAt = updateHavrutaDto.getUpdatedAt();
    }


}
