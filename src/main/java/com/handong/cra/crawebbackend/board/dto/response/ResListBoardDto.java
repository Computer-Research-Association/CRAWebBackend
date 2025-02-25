package com.handong.cra.crawebbackend.board.dto.response;

import com.handong.cra.crawebbackend.board.domain.Category;
import com.handong.cra.crawebbackend.board.dto.ListBoardDto;
import com.handong.cra.crawebbackend.havruta.dto.HavrutaDto;
import com.handong.cra.crawebbackend.user.dto.response.ResUserDetailDto;
import com.handong.cra.crawebbackend.util.BoardMDParser;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.commonmark.parser.Parser;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Schema(description = "Board 리스트 정보 데이터 전달 DTO")
public class ResListBoardDto {
    @Schema(description = "글 id")
    private Long id;

    @Schema(description = "작성자 id")
    private Long userId;

    @Schema(description = "글 제목")
    private String title;

    @Schema(description = "글 내용")
    private String content;

    @Schema(description = "글 카테고리")
    private Category category;

    @Schema(description = "글 좋아요 수")
    private Long likeCount;

    @Schema(description = "글 조회수")
    private Long view;

    @Schema(description = "글 작성 시간")
    private LocalDateTime createdAt;

    @Schema(description = "글 수정 시간")
    private LocalDateTime updatedAt;

    private HavrutaDto havrutaDto;

    private Integer commentCount;

    private ResUserDetailDto resUserDetailDto = new ResUserDetailDto();

    public ResListBoardDto(ListBoardDto listBoardDto) {
        this.id = listBoardDto.getId();
        this.userId = listBoardDto.getId();
        this.title = listBoardDto.getTitle();

        // 길이 제한
        String temp = BoardMDParser.extractPlainText(listBoardDto.getContent()).replace('\n', ' ');
        if (temp.length() > 90) temp = temp.substring(0, 90) + "...";
        this.content = temp;

        this.category = listBoardDto.getCategory();
        this.likeCount = listBoardDto.getLikeCount();
        this.view = listBoardDto.getView();
        this.createdAt = listBoardDto.getCreatedAt();
        this.updatedAt = listBoardDto.getUpdatedAt();

        if (listBoardDto.getHavrutaDto() != null) {
            this.havrutaDto = listBoardDto.getHavrutaDto();
        }
        this.commentCount = listBoardDto.getCommentCount();

        this.resUserDetailDto = ResUserDetailDto.from(listBoardDto.getUserDetailDto());

    }

    public static ResListBoardDto from(ListBoardDto listBoardDto) {
        return new ResListBoardDto(listBoardDto);
    }
}
