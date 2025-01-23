package com.handong.cra.crawebbackend.havruta.dto.havrutaboard;

import com.handong.cra.crawebbackend.board.domain.Board;
import com.handong.cra.crawebbackend.board.dto.ListBoardDto;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
public class ListHavrutaBoardDto extends ListBoardDto {

    private String className;
    private String professor;
    public ListHavrutaBoardDto (Board board){
        super(board);
        this.className = board.getHavruta().getClassName();
        this.professor = board.getHavruta().getProfessor();
    }

    public static ListHavrutaBoardDto from (Board board){
        if (board.getDeleted()) return null;
        else return new ListHavrutaBoardDto(board);
    }
}
