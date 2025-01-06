package com.handong.cra.crawebbackend.comment.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ReqCreateCommentDto {
    //-----------------------------------------
    // TODO : 유저 데이터 제거 (security 활용)
    private Long userId;
    //-----------------------------------------

    private Long parentCommentId;
    private String content;
}
