package com.handong.cra.crawebbackend.user.dto.request;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ReqUpdateUserPasswordDto {
    private String code;
    private String password;
}
