package com.handong.cra.crawebbackend.havruta.dto.havrutaboard.response;

import com.handong.cra.crawebbackend.board.dto.response.ResUpdateBoardDto;
import com.handong.cra.crawebbackend.havruta.dto.havrutaboard.UpdateHavrutaBoardDto;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ResUpdateHavrutaBoardDto extends ResUpdateBoardDto {

    private Long havrutaId;
    private String className;
    private String professor;

    public ResUpdateHavrutaBoardDto(UpdateHavrutaBoardDto updateHavrutaBoardDto){
        super(updateHavrutaBoardDto);
        this.havrutaId = updateHavrutaBoardDto.getHavrutaId();
        this.className = updateHavrutaBoardDto.getClassName();
        this.professor = updateHavrutaBoardDto.getProfessor();
    }

    public static ResUpdateHavrutaBoardDto from(UpdateHavrutaBoardDto updateHavrutaBoardDto){
        return new ResUpdateHavrutaBoardDto(updateHavrutaBoardDto);
    }
}
