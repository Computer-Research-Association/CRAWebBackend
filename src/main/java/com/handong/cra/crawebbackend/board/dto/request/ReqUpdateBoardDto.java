package com.handong.cra.crawebbackend.board.dto.request;

import com.handong.cra.crawebbackend.board.domain.Board;
import com.handong.cra.crawebbackend.board.domain.Category;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ReqUpdateBoardDto {
    //-----------------------------------------
    // TODO : 유저 데이터 제거 (security 활용)
    private Long userId;
    //-----------------------------------------

    private Board deleted;
    private String title;
    private String content;
    private Category category;
    private List<String> imageUrls;

    // TODO: add havrutaid
}
