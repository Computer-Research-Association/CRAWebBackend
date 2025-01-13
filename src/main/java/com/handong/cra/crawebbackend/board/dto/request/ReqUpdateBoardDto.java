package com.handong.cra.crawebbackend.board.dto.request;

import com.handong.cra.crawebbackend.board.domain.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Schema(description = "Board 수정 데이터 전달 DTO")
public class ReqUpdateBoardDto {
    //-----------------------------------------
    // TODO : 유저 데이터 제거 (security 활용)
    @Schema(description = "유저의 데이터 (이후 삭제 예정)")
    private Long userId;
    //-----------------------------------------

    @Schema(description = "글 삭제 여부")
    private Boolean deleted;
    @Schema(description = "글 제목")
    private String title;
    @Schema(description = "글 내용")
    private String content;
    @Schema(description = "사진 주소 목록")
    private List<String> imageUrls;
}
