package com.handong.cra.crawebbackend.auth.dto.response;

import com.handong.cra.crawebbackend.auth.dto.SignupDto;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ResSignupDto {
    private String username;
    private String email;
    private String name;
    private String githubId;
    private Long studentNumber;
    private String term;

    public ResSignupDto(SignupDto signupDto) {
        this.username = signupDto.getUsername();
        this.email = signupDto.getEmail();
        this.name = signupDto.getName();
        this.githubId = signupDto.getGithubId();
        this.studentNumber = signupDto.getStudentNumber();
        this.term = signupDto.getTerm();
    }

    public static ResSignupDto from(SignupDto signupDto) {
        return new ResSignupDto(signupDto);
    }
}
