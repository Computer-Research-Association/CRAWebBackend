package com.handong.cra.crawebbackend.board.dto.request;

import com.handong.cra.crawebbackend.havruta.dto.HavrutaDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Schema(description = "Board 생성 데이터 전달 DTO")
public class ReqCreateBoardDto {

    @NotNull
    @Schema(description = "글 제목")
    private String title;

    @NotNull
    @Schema(description = "글 내용")
    private String content;

    @NotNull
    @Schema(description = "글 카테고리")
    private Integer category;

    @Schema(description = "사진들 url")
    private List<String> imageUrls;

    @Schema(description = "하브루타인 경우만 전달")
    private HavrutaDto havrutaDto;
}
