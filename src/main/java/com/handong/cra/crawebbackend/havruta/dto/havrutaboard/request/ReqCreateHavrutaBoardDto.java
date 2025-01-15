package com.handong.cra.crawebbackend.havruta.dto.havrutaboard.request;

import com.handong.cra.crawebbackend.board.dto.request.ReqCreateBoardDto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ReqCreateHavrutaBoardDto extends ReqCreateBoardDto {

    private Long havrutaId;
}
