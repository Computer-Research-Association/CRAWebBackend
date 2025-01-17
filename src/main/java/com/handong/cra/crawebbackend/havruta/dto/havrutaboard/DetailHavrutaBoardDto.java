package com.handong.cra.crawebbackend.havruta.dto.havrutaboard;

import com.handong.cra.crawebbackend.board.domain.Board;
import com.handong.cra.crawebbackend.board.dto.DetailBoardDto;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
public class DetailHavrutaBoardDto extends DetailBoardDto {

    private String className;
    private String professor;
    public DetailHavrutaBoardDto(Board board){
        super(board);
        this.className = board.getHavruta().getClassName();
        this.professor = board.getHavruta().getProfessor();
    }

    public static DetailHavrutaBoardDto from (Board board){

        return new DetailHavrutaBoardDto(board);
    }
}
