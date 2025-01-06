package com.handong.cra.crawebbackend.comment.dto;

import com.handong.cra.crawebbackend.comment.domain.Comment;
import com.handong.cra.crawebbackend.comment.dto.request.ReqUpdateCommentDto;
import com.handong.cra.crawebbackend.comment.dto.response.ResUpdateCommentDto;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class UpdateCommentDto {
    private Long id;
    private String content;
    private Boolean deleted;


    public UpdateCommentDto(ReqUpdateCommentDto reqUpdateCommentDto, Long id) {
        this.id = id;
        this.content = reqUpdateCommentDto.getContent();
        this.deleted = reqUpdateCommentDto.getDeleted();
    }

    public UpdateCommentDto(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.deleted = comment.getDeleted();
    }

    public static UpdateCommentDto of(ReqUpdateCommentDto reqUpdateCommentDto, Long id) {
        return new UpdateCommentDto(reqUpdateCommentDto, id);
    }

    public static UpdateCommentDto from(Comment comment){
        return new UpdateCommentDto(comment);
    }
}
