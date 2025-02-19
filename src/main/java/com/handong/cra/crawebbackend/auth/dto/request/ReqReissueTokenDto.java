package com.handong.cra.crawebbackend.auth.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ReqReissueTokenDto {
    private Long userId;
    @NotNull
    private String refreshToken;
}
