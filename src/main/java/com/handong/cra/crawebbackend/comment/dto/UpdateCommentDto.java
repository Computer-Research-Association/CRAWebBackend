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


    public UpdateCommentDto(Long userId, ReqUpdateCommentDto reqUpdateCommentDto, Long id) {
        this.id = id;
        this.content = reqUpdateCommentDto.getContent();
        this.deleted = reqUpdateCommentDto.getDeleted();
    }

    public UpdateCommentDto(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.deleted = comment.getDeleted();
    }

    public static UpdateCommentDto of(Long userId, ReqUpdateCommentDto reqUpdateCommentDto, Long id) {
        return new UpdateCommentDto(userId, reqUpdateCommentDto, id);
    }

    public static UpdateCommentDto from(Comment comment) {
        return new UpdateCommentDto(comment);
    }
}
