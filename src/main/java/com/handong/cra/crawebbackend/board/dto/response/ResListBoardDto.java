package com.handong.cra.crawebbackend.board.dto.response;

import com.handong.cra.crawebbackend.board.domain.Category;
import com.handong.cra.crawebbackend.board.dto.ListBoardDto;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ResListBoardDto {
    private Long id;
    private Long userId;
    private String title;
    private String content;
    private Category category;
    private Long likeCount;
    private Long view;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    //-------------------------------
    // TODO: add havrutaid
    private Long havrutaId;
    //-------------------------------

    public ResListBoardDto(ListBoardDto listBoardDto) {
        this.id = listBoardDto.getId();
        this.userId = listBoardDto.getId();
        this.title = listBoardDto.getTitle();

        // 길이 제한
        String temp = listBoardDto.getContent();
        if (temp.length() > 90) temp = listBoardDto.getContent().substring(0, 90) + "...";

        this.content = temp;


        this.category = listBoardDto.getCategory();
        this.likeCount = listBoardDto.getLikeCount();
        this.view = listBoardDto.getView();
        this.createdAt = listBoardDto.getCreatedAt();
        this.updatedAt = listBoardDto.getUpdatedAt();
    }

    public static ResListBoardDto from(ListBoardDto listBoardDto) {
        return new ResListBoardDto(listBoardDto);
    }
}
