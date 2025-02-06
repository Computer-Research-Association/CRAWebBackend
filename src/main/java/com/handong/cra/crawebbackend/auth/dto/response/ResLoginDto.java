package com.handong.cra.crawebbackend.auth.dto.response;

import com.handong.cra.crawebbackend.auth.dto.LoginDto;
import com.handong.cra.crawebbackend.auth.dto.TokenDto;
import com.handong.cra.crawebbackend.user.dto.UserDetailDto;
import com.handong.cra.crawebbackend.user.dto.response.ResUserDetailDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ResLoginDto {
    private ResUserDetailDto resUserDetailDto;
    private ResTokenDto resTokenDto;

//    public ResLoginDto(ResUserDetailDto resUserDetailDto, ResTokenDto resTokenDto) {
//        this.resUserDetailDto = resUserDetailDto;
//        this.resTokenDto = resTokenDto;
//    }


    public static ResLoginDto of(UserDetailDto userDetailDto, TokenDto tokenDto) {
        return new ResLoginDto(ResUserDetailDto.from(userDetailDto), ResTokenDto.from(tokenDto));
    }

    public static ResLoginDto from(LoginDto loginDto) {
        return ResLoginDto.of(loginDto.getUserDetailDto(), loginDto.getTokenDto());
    }
}
