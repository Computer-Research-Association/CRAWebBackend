package com.handong.cra.crawebbackend.auth.dto;

import com.handong.cra.crawebbackend.auth.dto.request.ReqReissueTokenDto;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
public class ReissueTokenDto {
    private Long userId;
    @NotNull
    private String refreshToken;

    public static ReissueTokenDto from(ReqReissueTokenDto reqReissueTokenDto) {
        return builder()
                .refreshToken(reqReissueTokenDto.getRefreshToken())
                .build();
    }
}
