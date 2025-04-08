package com.handong.cra.crawebbackend.comment.controller;

import com.handong.cra.crawebbackend.auth.domain.CustomUserDetails;
import com.handong.cra.crawebbackend.comment.dto.CreateCommentDto;
import com.handong.cra.crawebbackend.comment.dto.UpdateCommentDto;
import com.handong.cra.crawebbackend.comment.dto.request.ReqCreateCommentDto;
import com.handong.cra.crawebbackend.comment.dto.request.ReqUpdateCommentDto;
import com.handong.cra.crawebbackend.comment.dto.response.ResCreateCommentDto;
import com.handong.cra.crawebbackend.comment.dto.response.ResUpdateCommentDto;
import com.handong.cra.crawebbackend.comment.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
@Slf4j
public class CommentController {
    private final CommentService commentService;


    @PostMapping("/{boardId}") // 댓글 작성
    @Operation(summary = "댓글 작성", description = "댓글, 대댓글 작성. 대댓글 작성시 부모 댓글에 ID 넣어서 보내주시면 됩니다")
    public ResponseEntity<ResCreateCommentDto> createComment(
            @AuthenticationPrincipal final CustomUserDetails customUserDetails,
            @PathVariable final Long boardId,
            @RequestBody final ReqCreateCommentDto reqCreateCommentDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResCreateCommentDto(commentService.createComment(
                        CreateCommentDto.of(boardId, customUserDetails.getUserId(), reqCreateCommentDto))));
    }

    @PutMapping("/{commentId}") // 댓글 수정
    @Operation(summary = "댓글 수정")
    public ResponseEntity<ResUpdateCommentDto> updateComment(
            @AuthenticationPrincipal final CustomUserDetails customUserDetails,
            @PathVariable final Long commentId,
            @RequestBody final ReqUpdateCommentDto reqUpdateCommentDto) {
        return ResponseEntity.ok().body(new ResUpdateCommentDto(commentService.updateComment(UpdateCommentDto.of(commentId, customUserDetails.getUserId(), reqUpdateCommentDto))));
    }

    @DeleteMapping("/{commentId}") // 댓글 삭제
    @Operation(summary = "댓글 삭제")
    public ResponseEntity<Void> deleteComment(
            @AuthenticationPrincipal final CustomUserDetails customUserDetails,
            @PathVariable final Long commentId) {
        if (commentService.deleteCommentById(UpdateCommentDto.of(commentId, customUserDetails.getUserId())))
            return ResponseEntity.ok().build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
