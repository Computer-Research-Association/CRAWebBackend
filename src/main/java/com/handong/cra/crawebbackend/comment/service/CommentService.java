package com.handong.cra.crawebbackend.comment.service;

import com.handong.cra.crawebbackend.comment.dto.CreateCommentDto;
import com.handong.cra.crawebbackend.comment.dto.ListCommentDto;
import com.handong.cra.crawebbackend.comment.dto.UpdateCommentDto;

import java.util.List;

public interface CommentService {

    /**
     * 댓글 생성
     *
     * @param createCommentDto 댓글 데이터 DTO
     */
    CreateCommentDto createComment(CreateCommentDto createCommentDto);

    /**
     * 댓글 수정
     *
     * @param updateCommentDto 수정할 댓글 데이터 DTO
     */
    UpdateCommentDto updateComment(UpdateCommentDto updateCommentDto);

    /**
     * 댓글 삭제
     *
     * @param updateCommentDto 삭제할 댓글 데이터 DTO
     */
    Boolean deleteCommentById(UpdateCommentDto updateCommentDto);
}
