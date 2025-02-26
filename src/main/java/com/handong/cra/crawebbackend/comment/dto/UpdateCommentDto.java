package com.handong.cra.crawebbackend.comment.dto;

import com.handong.cra.crawebbackend.comment.domain.Comment;
import com.handong.cra.crawebbackend.comment.dto.request.ReqUpdateCommentDto;
import com.handong.cra.crawebbackend.user.dto.UserDetailDto;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class UpdateCommentDto {
    private Long id;
    private Long userId;
    private UserDetailDto userDetailDto;
    private String content;
    private Boolean deleted;


    public UpdateCommentDto(Long userId, ReqUpdateCommentDto reqUpdateCommentDto, Long commentId) {
        this(userId, commentId);
        this.content = reqUpdateCommentDto.getContent();
        this.deleted = reqUpdateCommentDto.getDeleted();
    }

    public UpdateCommentDto(Comment comment) {
        this.id = comment.getId();
        this.userDetailDto = UserDetailDto.from(comment.getUser());
        this.content = comment.getContent();
        this.deleted = comment.getDeleted();
    }

    public UpdateCommentDto(Long userId, Long commentId) {
        this.userId = userId;
        this.id = commentId;
    }

    public static UpdateCommentDto of(Long commentId, Long userId, ReqUpdateCommentDto reqUpdateCommentDto) {
        return new UpdateCommentDto(userId, reqUpdateCommentDto, commentId);
    }
    public static UpdateCommentDto of(Long commentId, Long userId) {
        return new UpdateCommentDto(userId, commentId);
    }

    public static UpdateCommentDto from(Comment comment) {
        return new UpdateCommentDto(comment);
    }
}
