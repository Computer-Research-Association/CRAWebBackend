package com.handong.cra.crawebbackend.auth.dto;

import com.handong.cra.crawebbackend.auth.dto.request.ReqReissueTokenDto;
import com.handong.cra.crawebbackend.user.domain.UserRoleEnum;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ReissueTokenDto {
    private String username;
    private UserRoleEnum role;
    private String refreshToken;

    public ReissueTokenDto(ReqReissueTokenDto reqReissueTokenDto) {
        this.username = reqReissueTokenDto.getUsername();
        this.role = reqReissueTokenDto.getRole();
        this.refreshToken = reqReissueTokenDto.getRefreshToken();
    }

    public static ReissueTokenDto from(ReqReissueTokenDto reqReissueTokenDto) {
        return new ReissueTokenDto(reqReissueTokenDto);
    }
}
