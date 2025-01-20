package com.handong.cra.crawebbackend.havruta.dto.havrutaboard.response;

import com.handong.cra.crawebbackend.board.dto.response.ResListBoardDto;
import com.handong.cra.crawebbackend.havruta.dto.havrutaboard.ListHavrutaBoardDto;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResListHavrutaBoardDto extends ResListBoardDto {

    private String className;
    private String professor;
    public ResListHavrutaBoardDto (ListHavrutaBoardDto listHavrutaBoardDto){
        super(listHavrutaBoardDto);
        this.className = listHavrutaBoardDto.getClassName();
        this.professor = listHavrutaBoardDto.getProfessor();
    }

    public static ResListHavrutaBoardDto from (ListHavrutaBoardDto listHavrutaBoardDto){
        return new ResListHavrutaBoardDto(listHavrutaBoardDto);
    }
}
