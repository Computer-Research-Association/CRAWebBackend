package com.handong.cra.crawebbackend.board.dto.response;

import com.handong.cra.crawebbackend.board.domain.Category;
import com.handong.cra.crawebbackend.board.dto.ListBoardDto;
import com.handong.cra.crawebbackend.user.dto.response.ResUserDetailDto;
import com.handong.cra.crawebbackend.util.BoardMDParser;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Schema(description = "Board 리스트 정보 데이터 전달 DTO")
public class ResListBoardDto {
    private Long id;
    private String title;
    private String content;
    private Category category;
    private Long likeCount;
    private Long view;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer commentCount;
    private ResUserDetailDto resUserDetailDto = new ResUserDetailDto();

    public ResListBoardDto(ListBoardDto listBoardDto) {
        this.id = listBoardDto.getId();
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
        this.commentCount = listBoardDto.getCommentCount();

        this.resUserDetailDto = ResUserDetailDto.from(listBoardDto.getUserDetailDto());

    }

    public static ResListBoardDto from(ListBoardDto listBoardDto) {
        if (listBoardDto == null) {
            return new ResListBoardDto();
        }
        return new ResListBoardDto(listBoardDto);
    }
}
