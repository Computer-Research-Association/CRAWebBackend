package com.handong.cra.crawebbackend.havruta.dto;

import com.handong.cra.crawebbackend.common.domain.BaseEntity;
import com.handong.cra.crawebbackend.havruta.domain.Havruta;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Setter
@Getter
@Builder
public class DetailHavrutaDto {
    private Long id;
    private String className;
    private List<Long> boards;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public DetailHavrutaDto(Havruta havruta) {
        this.id = havruta.getId();
        this.className= havruta.getClassName();
        // Entity => id
        this.boards =havruta.getBoards().stream().map(BaseEntity::getId).toList();
        this.createdAt = havruta.getCreatedAt();
        this.updatedAt = havruta.getUpdatedAt();
    }
}
