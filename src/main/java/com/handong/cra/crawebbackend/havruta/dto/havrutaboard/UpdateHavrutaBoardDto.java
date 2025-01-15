package com.handong.cra.crawebbackend.havruta.dto.havrutaboard;

import com.handong.cra.crawebbackend.board.domain.Board;
import com.handong.cra.crawebbackend.board.dto.UpdateBoardDto;
import com.handong.cra.crawebbackend.havruta.dto.havrutaboard.request.ReqUpdateHavrutaBoardDto;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UpdateHavrutaBoardDto extends UpdateBoardDto {

    private Long havrutaId;

    private String className;
    private String professor;

    public UpdateHavrutaBoardDto(ReqUpdateHavrutaBoardDto reqUpdateHavrutaBoardDto){
        super(reqUpdateHavrutaBoardDto);
        this.havrutaId = reqUpdateHavrutaBoardDto.getHavrutaId();
    }

    public UpdateHavrutaBoardDto(Board board) {
        super(board);
        this.havrutaId = board.getHavruta().getId();
        this.className = board.getHavruta().getClassName();
        this.professor = board.getHavruta().getProfessor();
    }

    public static UpdateHavrutaBoardDto of(ReqUpdateHavrutaBoardDto reqUpdateHavrutaBoardDto){
        return new UpdateHavrutaBoardDto(reqUpdateHavrutaBoardDto);
    }

    public static UpdateHavrutaBoardDto from(Board board){
        return new UpdateHavrutaBoardDto(board);
    }

}
