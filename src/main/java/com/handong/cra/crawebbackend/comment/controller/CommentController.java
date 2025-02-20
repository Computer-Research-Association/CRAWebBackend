package com.handong.cra.crawebbackend.comment.controller;

import com.handong.cra.crawebbackend.auth.domain.CustomUserDetails;
import com.handong.cra.crawebbackend.comment.dto.CreateCommentDto;
import com.handong.cra.crawebbackend.comment.dto.UpdateCommentDto;
import com.handong.cra.crawebbackend.comment.dto.request.ReqCreateCommentDto;
import com.handong.cra.crawebbackend.comment.dto.request.ReqUpdateCommentDto;
import com.handong.cra.crawebbackend.comment.dto.response.ResCreateCommentDto;
import com.handong.cra.crawebbackend.comment.dto.response.ResListCommentDto;
import com.handong.cra.crawebbackend.comment.dto.response.ResUpdateCommentDto;
import com.handong.cra.crawebbackend.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
@Slf4j
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/{boardId}")
    public ResponseEntity<List<ResListCommentDto>> getCommentsByBoardId(@PathVariable Long boardId) {
        return ResponseEntity.ok().body(commentService.getCommentsByBoardId(boardId)
                .stream().map(ResListCommentDto::from).toList());
    }

    @GetMapping("/count/{boardId}")
    public ResponseEntity<Long> getCommentCount(@PathVariable Long boardId) {
        return ResponseEntity.ok(commentService.getCommentCount(boardId));
    }

    @PostMapping("/{boardId}")
    public ResponseEntity<ResCreateCommentDto> createComment(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable Long boardId, @RequestBody ReqCreateCommentDto reqCreateCommentDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResCreateCommentDto(
                        commentService.createComment(CreateCommentDto.of(boardId, customUserDetails.getUserId(), reqCreateCommentDto))));
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<ResUpdateCommentDto> updateComment(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable Long commentId, @RequestBody ReqUpdateCommentDto reqUpdateCommentDto) {
        return ResponseEntity.ok().body(new ResUpdateCommentDto(commentService.updateComment(UpdateCommentDto.of(commentId, customUserDetails.getUserId(), reqUpdateCommentDto))));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable Long commentId) {
        // success

        if (commentService.deleteCommentById(UpdateCommentDto.of(commentId, customUserDetails.getUserId())))
            return ResponseEntity.ok().build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
