package com.handong.cra.crawebbackend.comment.dto;

import com.handong.cra.crawebbackend.comment.dto.request.ReqCreateCommentDto;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class CreateCommentDto {

    private Long userId;
    private Long boardId;
    private String content;
    private Long parentCommentId;


    private Long id = null;
    private LocalDateTime createdAt = null;


    public CreateCommentDto(ReqCreateCommentDto reqCreateCommentDto, Long userId, Long boardId) {
        this.userId = userId;
        this.content = reqCreateCommentDto.getContent();
        this.boardId = reqCreateCommentDto.getBoardId();
        this.parentCommentId = reqCreateCommentDto.getParentCommentId();
    }

    // TODO : user logic
    public static CreateCommentDto of(ReqCreateCommentDto reqCreateBoardDto, Long userId, Long boardId) {
        return new CreateCommentDto(reqCreateBoardDto, userId, boardId);
    }
}
