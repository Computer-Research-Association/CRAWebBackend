package com.handong.cra.crawebbackend.auth.dto.request;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ReqLoginDto {
    private String username;
    private String password;
}
