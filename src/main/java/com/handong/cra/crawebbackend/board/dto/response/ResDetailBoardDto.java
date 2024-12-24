package com.handong.cra.crawebbackend.board.dto.response;

import com.handong.cra.crawebbackend.board.domain.Board;
import com.handong.cra.crawebbackend.board.domain.Category;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ResDetailBoardDto {
    private Long id;
    private Long userId;
    private String title;
    private String content;
    private Category category;
    private Long likeCount;
    private Long view;
    private List<String> imageUrls;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    // TODO: add havrutaid



    public ResDetailBoardDto(Board board){
        this.id = board.getId();
        this.userId = board.getUser().getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.category = board.getCategory();
        this.likeCount = board.getLikeCount();
        this.view = board.getView();
        this.imageUrls = board.getImageUrls();
        this.createdAt = board.getCreatedAt();
        this.updatedAt = board.getUpdatedAt();
    }
}
