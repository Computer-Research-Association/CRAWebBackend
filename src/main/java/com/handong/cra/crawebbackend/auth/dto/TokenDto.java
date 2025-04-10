package com.handong.cra.crawebbackend.auth.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TokenDto {
    private Long userId;
    private String accessToken;
    private String refreshToken;

    public static TokenDto of (Long userId, String accessToken, String refreshToken) {
        return new TokenDto(userId, accessToken,refreshToken);
    }

}
