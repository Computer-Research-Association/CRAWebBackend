package com.handong.cra.crawebbackend.board.dto.response;

import com.handong.cra.crawebbackend.board.domain.Category;
import com.handong.cra.crawebbackend.board.dto.ListBoardDto;
import com.handong.cra.crawebbackend.tag.dto.response.ResTagDto;
import com.handong.cra.crawebbackend.user.dto.response.ResUserDetailDto;
import com.handong.cra.crawebbackend.util.BoardMDParser;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
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
    private List<ResTagDto> tags;

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
        this.tags = listBoardDto.getTags();

    }

    public static ResListBoardDto from(ListBoardDto listBoardDto) {
        if (listBoardDto == null) {
            return new ResListBoardDto();
        }
        return new ResListBoardDto(listBoardDto);
    }
}
