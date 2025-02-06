package com.handong.cra.crawebbackend.auth.dto.response;

import com.handong.cra.crawebbackend.auth.dto.TokenDto;
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

    public ResTokenDto(TokenDto tokenDto) {
        this.userId = tokenDto.getUserId();
        this.accessToken = tokenDto.getAccessToken();
        this.refreshToken = tokenDto.getRefreshToken();
    }

    public static ResTokenDto of(Long userId, String accessToken, String refreshToken) {
        return new ResTokenDto(userId, accessToken, refreshToken);
    }

    public static ResTokenDto from(TokenDto tokenDto) {
        return new ResTokenDto(tokenDto);

    }
}