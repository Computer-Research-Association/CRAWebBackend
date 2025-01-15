package com.handong.cra.crawebbackend.auth.dto.request;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ReqReissueTokenDto {
    private Long userId;
    private String refreshToken;
}
