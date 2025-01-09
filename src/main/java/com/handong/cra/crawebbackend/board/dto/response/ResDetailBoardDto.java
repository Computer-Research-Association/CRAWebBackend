package com.handong.cra.crawebbackend.board.dto.response;

import com.handong.cra.crawebbackend.board.domain.Category;
import com.handong.cra.crawebbackend.board.dto.DetailBoardDto;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
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

    public ResDetailBoardDto(DetailBoardDto detailBoardDto) {
        this.id = detailBoardDto.getId();
        this.userId = detailBoardDto.getUserId();
        this.title = detailBoardDto.getTitle();
        this.content = detailBoardDto.getContent();
        this.category = detailBoardDto.getCategory();
        this.likeCount = detailBoardDto.getLikeCount();
        this.view = detailBoardDto.getView();
        this.imageUrls = detailBoardDto.getImageUrls();
        this.createdAt = detailBoardDto.getCreatedAt();
        this.updatedAt = detailBoardDto.getUpdatedAt();
    }


    public static ResDetailBoardDto from(DetailBoardDto detailBoardDto) {
        return new ResDetailBoardDto(detailBoardDto);
    }
}
