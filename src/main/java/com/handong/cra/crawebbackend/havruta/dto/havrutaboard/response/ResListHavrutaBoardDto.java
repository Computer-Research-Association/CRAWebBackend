package com.handong.cra.crawebbackend.havruta.dto.havrutaboard.response;

import com.handong.cra.crawebbackend.board.dto.response.ResListBoardDto;
import com.handong.cra.crawebbackend.havruta.dto.havrutaboard.ListHavrutaBoardDto;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResListHavrutaBoardDto extends ResListBoardDto {

    public ResListHavrutaBoardDto (ListHavrutaBoardDto listHavrutaBoardDto){
        super(listHavrutaBoardDto);
    }

    public static ResListHavrutaBoardDto from (ListHavrutaBoardDto listHavrutaBoardDto){
        return new ResListHavrutaBoardDto(listHavrutaBoardDto);
    }
}
