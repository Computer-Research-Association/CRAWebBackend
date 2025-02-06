package com.handong.cra.crawebbackend.auth.dto;

import com.handong.cra.crawebbackend.auth.dto.request.ReqLoginDto;
import com.handong.cra.crawebbackend.user.dto.UserDetailDto;
import lombok.*;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class LoginDto {
    private String username;
    private String password;
    private UserDetailDto userDetailDto;
    private TokenDto tokenDto;

    public LoginDto(ReqLoginDto reqLoginDto) {
        this.username = reqLoginDto.getUsername();
        this.password = reqLoginDto.getPassword();
    }

    public LoginDto(UserDetailDto userDetailDto, TokenDto tokenDto) {
        this.userDetailDto = userDetailDto;
        this.tokenDto = tokenDto;
    }

    public static LoginDto from(ReqLoginDto reqLoginDto) {
        return new LoginDto(reqLoginDto);
    }

    public static LoginDto of (UserDetailDto userDetailDto, TokenDto tokenDto){
        return new LoginDto(userDetailDto, tokenDto);
    }

}
