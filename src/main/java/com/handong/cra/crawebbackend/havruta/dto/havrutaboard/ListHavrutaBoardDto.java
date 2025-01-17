package com.handong.cra.crawebbackend.havruta.dto.havrutaboard;

import com.handong.cra.crawebbackend.board.domain.Board;
import com.handong.cra.crawebbackend.board.dto.ListBoardDto;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ListHavrutaBoardDto extends ListBoardDto {

    public ListHavrutaBoardDto (Board board){
        super(board);
    }

    public static ListHavrutaBoardDto from (Board board){
        if (board.getDeleted()) return null;
        else return new ListHavrutaBoardDto(board);
    }
}
