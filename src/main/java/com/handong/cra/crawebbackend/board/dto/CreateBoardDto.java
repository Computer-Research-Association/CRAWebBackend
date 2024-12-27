package com.handong.cra.crawebbackend.board.dto;

import com.handong.cra.crawebbackend.board.domain.Board;
import com.handong.cra.crawebbackend.board.domain.Category;
import com.handong.cra.crawebbackend.board.dto.request.ReqCreateBoardDto;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class CreateBoardDto {

    private Long userId;
    private String title;
    private String content;
    private Category category;
    private List<String> imageUrls;

    private Long id = null;
    private LocalDateTime createdAt = null;

    // TODO: add havrutaid


    public CreateBoardDto(ReqCreateBoardDto reqCreateBoardDto, Long userId) {
        this.userId = userId;
        this.title = reqCreateBoardDto.getTitle();
        this.content = reqCreateBoardDto.getContent();
        this.category = reqCreateBoardDto.getCategory();
        this.imageUrls = reqCreateBoardDto.getImageUrls();
        // TODO: add havrutaid

    }

    // TODO : user logic
    public static CreateBoardDto of(ReqCreateBoardDto reqCreateBoardDto, Long userId) {
        return new CreateBoardDto(reqCreateBoardDto, userId);
    }
}
