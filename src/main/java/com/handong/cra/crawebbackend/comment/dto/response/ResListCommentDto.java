package com.handong.cra.crawebbackend.comment.dto.response;

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
    private List<ResListCommentDto> commentList;
    private String content;
    private Long likeCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ResListCommentDto(ListCommentDto listCommentDto) {
        this.id = listCommentDto.getId();
        this.userId = listCommentDto.getUserId();
        this.boardId = listCommentDto.getBoardId();
        this.commentList = listCommentDto.getCommentDtoList().stream().map(ResListCommentDto::from).toList();
        this.content = listCommentDto.getContent();
        this.likeCount = listCommentDto.getLikeCount();
        this.createdAt = listCommentDto.getCreatedAt();
        this.updatedAt = listCommentDto.getUpdatedAt();
    }

    public static ResListCommentDto from(ListCommentDto listCommentDto){
        return new ResListCommentDto(listCommentDto);
    }
}
