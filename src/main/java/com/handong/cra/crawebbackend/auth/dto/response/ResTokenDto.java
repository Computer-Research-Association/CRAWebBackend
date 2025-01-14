package com.handong.cra.crawebbackend.auth.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ResTokenDto {
    private Long id;
    private String accessToken;
    private String refreshToken;
}
