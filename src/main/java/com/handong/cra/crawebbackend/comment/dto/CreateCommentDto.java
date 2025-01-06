package com.handong.cra.crawebbackend.comment.dto;

import com.handong.cra.crawebbackend.board.domain.Board;
import com.handong.cra.crawebbackend.board.domain.Category;
import com.handong.cra.crawebbackend.board.dto.request.ReqCreateBoardDto;
import com.handong.cra.crawebbackend.comment.dto.request.ReqCreateCommentDto;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

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
        this.boardId = boardId;
        this.content = reqCreateCommentDto.getContent();
        this.boardId = reqCreateCommentDto.getBoardId();
    }

    // TODO : user logic
    public static CreateCommentDto of(ReqCreateCommentDto reqCreateBoardDto, Long userId, Long boardId) {
        return new CreateCommentDto(reqCreateBoardDto, userId, boardId);
    }
}
