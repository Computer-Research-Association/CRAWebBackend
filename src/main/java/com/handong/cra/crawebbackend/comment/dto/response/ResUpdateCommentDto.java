package com.handong.cra.crawebbackend.comment.dto.response;

import com.handong.cra.crawebbackend.comment.dto.UpdateCommentDto;
import com.handong.cra.crawebbackend.user.dto.response.ResUserDetailDto;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ResUpdateCommentDto {
    private Long id;
    private ResUserDetailDto resUserDetailDto;
    private String content;
    private Boolean deleted;

    public ResUpdateCommentDto(UpdateCommentDto updateCommentDto) {
        this.id = updateCommentDto.getId();
        this.resUserDetailDto = ResUserDetailDto.from(updateCommentDto.getUserDetailDto());
        this.content = updateCommentDto.getContent();
        this.deleted = updateCommentDto.getDeleted();
    }

    public static ResUpdateCommentDto from(UpdateCommentDto updateCommentDto) {
        return new ResUpdateCommentDto(updateCommentDto);
    }
}
