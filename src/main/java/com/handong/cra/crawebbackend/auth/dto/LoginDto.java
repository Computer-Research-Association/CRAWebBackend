package com.handong.cra.crawebbackend.auth.dto;

import com.handong.cra.crawebbackend.auth.dto.request.ReqLoginDto;
import lombok.*;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class LoginDto {
    private String username;
    private String password;

    public LoginDto(ReqLoginDto reqLoginDto) {
        this.username = reqLoginDto.getUsername();
        this.password = reqLoginDto.getPassword();
    }

    public static LoginDto from(ReqLoginDto reqLoginDto) {
        return new LoginDto(reqLoginDto);
    }
}
