package com.handong.cra.crawebbackend.comment.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ReqUpdateCommentDto {
    private Boolean deleted;
    private String content;
}
