package com.handong.cra.crawebbackend.comment.dto;

import com.handong.cra.crawebbackend.comment.domain.Comment;
import com.handong.cra.crawebbackend.comment.dto.request.ReqCreateCommentDto;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CreateCommentDto {

    private Long userId;
    private Long boardId;
    private String content;
    private Long parentCommentId;


    private Long id = null;
    private LocalDateTime createdAt = null;


    public CreateCommentDto(Long boardId, Long userId, ReqCreateCommentDto reqCreateCommentDto) {
        this.userId = userId;
        this.content = reqCreateCommentDto.getContent();
        this.boardId = boardId;
        this.parentCommentId = reqCreateCommentDto.getParentCommentId();
    }

    public CreateCommentDto(Comment comment) {
        this.id = comment.getId();
        this.createdAt = comment.getCreatedAt();
        this.userId = comment.getUser().getId();
        this.content = comment.getContent();
        this.boardId = comment.getBoard().getId();
        if (comment.getParentComment() != null) this.parentCommentId = comment.getParentComment().getId();
    }

    public static CreateCommentDto of(Long boardId, Long userId, ReqCreateCommentDto reqCreateBoardDto) {
        return new CreateCommentDto(boardId, userId, reqCreateBoardDto);
    }

    public static CreateCommentDto from(Comment comment) {
        return new CreateCommentDto(comment);
    }
}
