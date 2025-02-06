package com.handong.cra.crawebbackend.comment.service;

import com.handong.cra.crawebbackend.comment.dto.CreateCommentDto;
import com.handong.cra.crawebbackend.comment.dto.ListCommentDto;
import com.handong.cra.crawebbackend.comment.dto.UpdateCommentDto;

import java.util.List;

public interface CommentService {
    public CreateCommentDto createComment(CreateCommentDto createCommentDto);
    public List<ListCommentDto> getCommentsByBoardId(Long boardId);
    public UpdateCommentDto updateComment(UpdateCommentDto updateCommentDto);
    public Boolean deleteCommentById(Long userId, Long id);
    public Long getCommentCount(Long boardId);
}
