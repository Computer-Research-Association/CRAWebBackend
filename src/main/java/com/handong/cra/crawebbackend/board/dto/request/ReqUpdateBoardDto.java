package com.handong.cra.crawebbackend.board.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Schema(description = "Board 수정 데이터 전달 DTO")
public class ReqUpdateBoardDto {
    @Schema(description = "글 제목")
    private String title;
    @Schema(description = "글 내용")
    private String content;
    @Schema(description = "사진 주소 목록")
    private List<String> imageUrls;
    @Schema(description = "파일 변경 여부")
    private Boolean isChangedFile;

    private Boolean deleted;

}
