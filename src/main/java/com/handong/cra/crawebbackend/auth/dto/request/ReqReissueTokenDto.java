package com.handong.cra.crawebbackend.auth.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
public class ReqReissueTokenDto {
    @NotNull
    private String refreshToken;
}
