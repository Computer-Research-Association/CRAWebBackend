package com.handong.cra.crawebbackend.havruta.dto.havrutaboard;

import com.handong.cra.crawebbackend.board.domain.Board;
import com.handong.cra.crawebbackend.board.dto.CreateBoardDto;
import com.handong.cra.crawebbackend.havruta.dto.havrutaboard.request.ReqCreateHavrutaBoardDto;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
public class CreateHavrutaBoardDto extends CreateBoardDto {

    private Long havrutaId;

    private String className;
    private String professor;

    public CreateHavrutaBoardDto(Board board) {
        super(board);
        this.havrutaId = board.getHavruta().getId();
        this.className = board.getHavruta().getClassName();
        this.professor = board.getHavruta().getProfessor();
    }
    public CreateHavrutaBoardDto(ReqCreateHavrutaBoardDto reqCreateHavrutaBoardDto){
        super(reqCreateHavrutaBoardDto);
        this.havrutaId = reqCreateHavrutaBoardDto.getHavrutaId();
    }

    public static CreateHavrutaBoardDto from(Board board){
        return new CreateHavrutaBoardDto(board);
    }

    public static CreateHavrutaBoardDto from(ReqCreateHavrutaBoardDto reqCreateHavrutaBoardDto){
        return new CreateHavrutaBoardDto(reqCreateHavrutaBoardDto);
    }
}
