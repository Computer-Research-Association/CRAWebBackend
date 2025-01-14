package com.handong.cra.crawebbackend.auth.dto.request;

import com.handong.cra.crawebbackend.user.domain.UserRoleEnum;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ReqReissueTokenDto {
    private String username;
    private UserRoleEnum role;
    private String refreshToken;
}
