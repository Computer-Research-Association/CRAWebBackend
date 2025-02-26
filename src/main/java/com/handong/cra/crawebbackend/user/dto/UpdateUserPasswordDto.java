package com.handong.cra.crawebbackend.user.dto;

import com.handong.cra.crawebbackend.user.dto.request.ReqUpdateUserPasswordDto;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UpdateUserPasswordDto {
    private String username;
    private String code;
    private String password;

    public UpdateUserPasswordDto(ReqUpdateUserPasswordDto reqUpdateUserPasswordDto) {
        this.code = reqUpdateUserPasswordDto.getCode();;
        this.password =reqUpdateUserPasswordDto.getPassword();
    }

    public static UpdateUserPasswordDto from(ReqUpdateUserPasswordDto reqUpdateUserPasswordDto) {
        return new UpdateUserPasswordDto(reqUpdateUserPasswordDto);
    }


}
