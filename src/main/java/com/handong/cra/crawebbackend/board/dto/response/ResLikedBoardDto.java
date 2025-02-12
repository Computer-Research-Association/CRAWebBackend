package com.handong.cra.crawebbackend.board.dto.response;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResLikedBoardDto {
    private Boolean liked;
    private Integer likes;

    public static ResLikedBoardDto of(Boolean isLike, Integer likes) {
        return new ResLikedBoardDto(isLike, likes);
    }
}
