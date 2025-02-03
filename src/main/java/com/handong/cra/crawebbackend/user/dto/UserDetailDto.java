package com.handong.cra.crawebbackend.user.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserDetailDto {
    private String name;
    private String term;
    private String githubId;
    private String greetingMessage;
    private Long studentNumber;


}
