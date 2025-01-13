package com.handong.cra.crawebbackend.havruta.dto.havrutaboard;

import com.handong.cra.crawebbackend.board.domain.Board;
import com.handong.cra.crawebbackend.board.dto.ListBoardDto;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ListHavrutaBoardDto extends ListBoardDto {

    public ListHavrutaBoardDto (Board board){
        super(board);
    }

    public static ListHavrutaBoardDto from (Board board){
        return new ListHavrutaBoardDto(board);
    }


}
