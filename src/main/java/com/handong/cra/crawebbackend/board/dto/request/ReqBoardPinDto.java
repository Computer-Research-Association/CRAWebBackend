package com.handong.cra.crawebbackend.board.dto.request;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReqBoardPinDto {
    private Long boardId;
    private Integer category;

}
