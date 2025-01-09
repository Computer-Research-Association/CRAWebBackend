package com.handong.cra.crawebbackend.board.dto.response;


import com.handong.cra.crawebbackend.board.dto.UpdateBoardDto;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ResUpdateBoardDto {
    private Long id;
    private Long userId;
    private Boolean deleted;
    private String title;
    private String content;
    private List<String> imageUrls;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ResUpdateBoardDto(UpdateBoardDto updateBoardDto) {
        this.id = updateBoardDto.getId();
        this.userId = updateBoardDto.getUserId();
        this.deleted = updateBoardDto.getDeleted();
        this.title = updateBoardDto.getTitle();
        this.content = updateBoardDto.getContent();
        this.imageUrls = updateBoardDto.getImageUrls();
        this.createdAt =  updateBoardDto.getCreatedAt();
        this.updatedAt = updateBoardDto.getUpdatedAt();
    }

    public static ResUpdateBoardDto from(UpdateBoardDto updateBoardDto) {
        return new ResUpdateBoardDto(updateBoardDto);
    }

    // TODO: add havrutaid
}
