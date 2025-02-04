package com.handong.cra.crawebbackend.account.dto.req;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor (access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ReqFindUsernameDto {
    private String name;
    private String email;
    private Integer studentId;
}
