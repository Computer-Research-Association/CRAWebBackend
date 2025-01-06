package com.handong.cra.crawebbackend.comment.controller;

import com.handong.cra.crawebbackend.comment.dto.CreateCommentDto;
import com.handong.cra.crawebbackend.comment.dto.UpdateCommentDto;
import com.handong.cra.crawebbackend.comment.dto.request.ReqCreateCommentDto;
import com.handong.cra.crawebbackend.comment.dto.request.ReqUpdateCommentDto;
import com.handong.cra.crawebbackend.comment.dto.response.ResCreateCommentDto;
import com.handong.cra.crawebbackend.comment.dto.response.ResListCommentDto;
import com.handong.cra.crawebbackend.comment.dto.response.ResUpdateCommentDto;
import com.handong.cra.crawebbackend.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/{boardId}")
    public ResponseEntity<List<ResListCommentDto>> getCommentsByBoardId(@PathVariable Long boardId) {
        return ResponseEntity.ok().body(commentService.getCommentsByBoardId(boardId)
                .stream().map(ResListCommentDto::from).toList());
    }

    @PostMapping("")
    public ResponseEntity<ResCreateCommentDto> createComment(@RequestBody ReqCreateCommentDto reqCreateCommentDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResCreateCommentDto(commentService.createComment(
                        CreateCommentDto.of(
                                reqCreateCommentDto,
                                reqCreateCommentDto.getUserId(),
                                reqCreateCommentDto.getBoardId()
                        )))
                );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResUpdateCommentDto> updateComment(@PathVariable Long id, @RequestBody ReqUpdateCommentDto reqUpdateCommentDto) {
        return ResponseEntity.ok().body(new ResUpdateCommentDto(commentService.updateComment(new UpdateCommentDto(reqUpdateCommentDto, id))));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        // success
        if (commentService.deleteCommentById(id))
            return ResponseEntity.ok().build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
