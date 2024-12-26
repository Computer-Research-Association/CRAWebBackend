package com.handong.cra.crawebbackend.comment.dto.response;

import com.handong.cra.crawebbackend.board.domain.Category;
import com.handong.cra.crawebbackend.comment.dto.ListCommentDto;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ResListCommentDto {
    private Long id;
    private Long userId;
    private Long boardId;
    private String content;
    private Long likeCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ResListCommentDto(ListCommentDto listCommentDto) {
        this.id = listCommentDto.getId();
        this.userId = listCommentDto.getUserId();
        this.boardId = listCommentDto.getBoardId();
        this.content = listCommentDto.getContent();
        this.likeCount = listCommentDto.getLikeCount();
        this.createdAt = listCommentDto.getCreatedAt();
        this.updatedAt = listCommentDto.getUpdatedAt();
    }
}
