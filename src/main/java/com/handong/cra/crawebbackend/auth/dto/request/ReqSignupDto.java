package com.handong.cra.crawebbackend.auth.dto.request;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ReqSignupDto {
    private String username;
    private String password;
    private String email;
    private String name;
    private String githubId;
    private Long studentId;
    private String term;
    private String code;
}
