package com.handong.cra.crawebbackend.board.dto.response;


import com.handong.cra.crawebbackend.board.domain.Board;
import com.handong.cra.crawebbackend.board.domain.Category;
import com.handong.cra.crawebbackend.board.dto.UpdateBoardDto;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ResUpdateBoardDto {
    private Long id = null;
    private Long userId;
    private Boolean deleted;
    private String title;
    private String content;
    private Category category;
    private List<String> imageUrls;

    public ResUpdateBoardDto(UpdateBoardDto updateBoardDto) {
        this.id = updateBoardDto.getId();
        this.userId = updateBoardDto.getUserId();
        this.deleted = updateBoardDto.getDeleted();
        this.title = updateBoardDto.getTitle();
        this.content = updateBoardDto.getContent();
        this.category = updateBoardDto.getCategory();
        this.imageUrls = updateBoardDto.getImageUrls();
    }

    public static ResUpdateBoardDto from(UpdateBoardDto updateBoardDto) {
        return new ResUpdateBoardDto(updateBoardDto);
    }

    // TODO: add havrutaid
}
