package com.handong.cra.crawebbackend.comment.dto;

import com.handong.cra.crawebbackend.comment.domain.Comment;
import com.handong.cra.crawebbackend.comment.dto.request.ReqUpdateCommentDto;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class UpdateCommentDto {
    private Long id;
    private Long userId;
    private String content;
    private Boolean deleted;


    public UpdateCommentDto(Long userId, ReqUpdateCommentDto reqUpdateCommentDto, Long commentId) {
        this.id = commentId;
        this.userId = userId;
        this.content = reqUpdateCommentDto.getContent();
        this.deleted = reqUpdateCommentDto.getDeleted();
    }

    public UpdateCommentDto(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.deleted = comment.getDeleted();
    }

    public static UpdateCommentDto of(Long commentId, Long userId, ReqUpdateCommentDto reqUpdateCommentDto) {
        return new UpdateCommentDto(userId, reqUpdateCommentDto, commentId);
    }

    public static UpdateCommentDto from(Comment comment) {
        return new UpdateCommentDto(comment);
    }
}
