package com.handong.cra.crawebbackend.havruta.dto;

import com.handong.cra.crawebbackend.board.domain.Board;
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
    private LocalDateTime updatedAt;

    public ListHavrutaDto(Board board) {
        this.id = board.getId();
        this.className = board.getHavruta().getClassName();
        this.professor = board.getHavruta().getProfessor();
        this.createdAt = board.getCreatedAt();
        this.updatedAt = board.getUpdatedAt();
    }

    public ListHavrutaDto(Havruta havruta) {
        this.id = havruta.getId();
        this.className = havruta.getClassName();
        this.professor = havruta.getProfessor();
        this.createdAt = havruta.getCreatedAt();
        this.updatedAt = havruta.getUpdatedAt();
    }

    public static ListHavrutaDto from(Board board){
        if(board.getDeleted()) return null;
        return new ListHavrutaDto(board);
    }

    public static ListHavrutaDto from(Havruta havruta){
        if(havruta.getDeleted()) return null;
        return new ListHavrutaDto(havruta);
    }
}
