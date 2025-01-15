package com.handong.cra.crawebbackend.board.dto.request;

import com.handong.cra.crawebbackend.board.domain.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.Calendar;
import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Schema(description = "Board 생성 데이터 전달 DTO")
public class ReqCreateBoardDto {
    //-----------------------------------------
    // TODO : 유저 데이터 제거 (security 활용)
    @Schema(description = "유저의 데이터 (이후 삭제 예정)")
    private Long userId;
    //-----------------------------------------

    @Schema(description = "글 제목")
    private String title;
    @Schema(description = "글 내용")
    private String content;
    @Schema(description = "글 카테고리")
    private Integer category;
    @Schema(description = "사진들 url")
    private List<String> imageUrls;
}
