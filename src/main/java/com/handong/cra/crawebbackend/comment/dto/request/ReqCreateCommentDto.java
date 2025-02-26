package com.handong.cra.crawebbackend.comment.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ReqCreateCommentDto {
    private Long parentCommentId;
    private String content;
}
