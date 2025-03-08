package com.handong.cra.crawebbackend.auth.dto.response;

import com.handong.cra.crawebbackend.auth.dto.TokenDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ResTokenDto {
    private String accessToken;
    private String refreshToken;

    public ResTokenDto(final TokenDto tokenDto) {
        this.accessToken = tokenDto.getAccessToken();
        this.refreshToken = tokenDto.getRefreshToken();
    }

    public static ResTokenDto of(final String accessToken, final String refreshToken) {
        return builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public static ResTokenDto from(final TokenDto tokenDto) {
        return builder()
                .accessToken(tokenDto.getAccessToken())
                .refreshToken(tokenDto.getRefreshToken())
                .build();
    }
}