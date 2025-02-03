package com.handong.cra.crawebbackend.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ResTokenDto {
    private Long userId;
    private String accessToken;
    private String refreshToken;

    public static ResTokenDto of(Long userId, String accessToken, String refreshToken) {
        return new ResTokenDto(userId, accessToken, refreshToken);
    }
}
