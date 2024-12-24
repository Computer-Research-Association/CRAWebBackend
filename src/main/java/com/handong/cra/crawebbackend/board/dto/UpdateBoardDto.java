package com.handong.cra.crawebbackend.board.dto;

import com.handong.cra.crawebbackend.board.domain.Board;
import com.handong.cra.crawebbackend.board.domain.Category;
import com.handong.cra.crawebbackend.board.dto.request.ReqUpdateBoardDto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class UpdateBoardDto {

    private Long id;
    private Long userId;
    private Boolean deleted;
    private String title;
    private String content;
    private Category category;
    private List<String> imageUrls;


    // TODO: add havrutaid


    public UpdateBoardDto(ReqUpdateBoardDto reqUpdateBoardDto, Long id) {
        // TODO : User Logic
        this.userId = reqUpdateBoardDto.getUserId();

        this.id = id;
        this.deleted = reqUpdateBoardDto.getDeleted();
        this.title = reqUpdateBoardDto.getTitle();
        this.content = reqUpdateBoardDto.getContent();
        this.category = reqUpdateBoardDto.getCategory();
        this.imageUrls = reqUpdateBoardDto.getImageUrls();
    }

    public UpdateBoardDto(Board board) {
        this.id = board.getId();
        this.deleted = board.getDeleted();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.category = board.getCategory();
        this.imageUrls = board.getImageUrls();
    }
}
