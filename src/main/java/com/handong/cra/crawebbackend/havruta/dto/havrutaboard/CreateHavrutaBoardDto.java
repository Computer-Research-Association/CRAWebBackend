package com.handong.cra.crawebbackend.havruta.dto.havrutaboard;

import com.handong.cra.crawebbackend.board.domain.Board;
import com.handong.cra.crawebbackend.board.dto.CreateBoardDto;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
public class CreateHavrutaBoardDto extends CreateBoardDto {
    private Long havrutaId;

    public CreateHavrutaBoardDto(Board board) {
        super(board);
        this.havrutaId = board.getHavruta().getId();
    }

    public static CreateHavrutaBoardDto from(Board board){
        return new CreateHavrutaBoardDto(board);
    }
}
