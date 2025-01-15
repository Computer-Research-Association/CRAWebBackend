package com.handong.cra.crawebbackend.havruta.dto.havrutaboard.request;

import com.handong.cra.crawebbackend.board.dto.request.ReqUpdateBoardDto;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ReqUpdateHavrutaBoardDto extends ReqUpdateBoardDto {

    private Long havrutaId;
}
