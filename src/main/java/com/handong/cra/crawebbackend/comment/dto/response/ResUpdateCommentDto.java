package com.handong.cra.crawebbackend.comment.dto.response;

import com.handong.cra.crawebbackend.comment.dto.UpdateCommentDto;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ResUpdateCommentDto {
    private Long id;
    private String content;
    private Boolean deleted;

    public ResUpdateCommentDto(UpdateCommentDto updateCommentDto) {
        this.id = updateCommentDto.getId();
        this.content = updateCommentDto.getContent();
        this.deleted = updateCommentDto.getDeleted();
    }
}
