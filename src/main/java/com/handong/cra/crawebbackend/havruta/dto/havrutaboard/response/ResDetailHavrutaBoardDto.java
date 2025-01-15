package com.handong.cra.crawebbackend.havruta.dto.havrutaboard.response;

import com.handong.cra.crawebbackend.board.dto.response.ResDetailBoardDto;
import com.handong.cra.crawebbackend.havruta.dto.havrutaboard.DetailHavrutaBoardDto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ResDetailHavrutaBoardDto extends ResDetailBoardDto {
    private String className;
    private String professor;
    public ResDetailHavrutaBoardDto(DetailHavrutaBoardDto detailHavrutaBoardDto){
        super(detailHavrutaBoardDto);
        this.className = detailHavrutaBoardDto.getClassName();
        this.professor = detailHavrutaBoardDto.getProfessor();
    }

    public static ResDetailHavrutaBoardDto from(DetailHavrutaBoardDto detailHavrutaBoardDto){
        return new ResDetailHavrutaBoardDto(detailHavrutaBoardDto);
    }

}
