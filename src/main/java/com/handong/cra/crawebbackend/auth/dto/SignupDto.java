package com.handong.cra.crawebbackend.auth.dto;

import com.handong.cra.crawebbackend.auth.dto.request.ReqSignupDto;
import com.handong.cra.crawebbackend.auth.dto.response.ResSignupDto;
import com.handong.cra.crawebbackend.user.domain.UserRoleEnum;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class SignupDto {
    private Long id;
    private String username;
    private String password;
    private String email;
    private String name;
    private String githubId;
    private Long studentNumber;
    private String term;
    private UserRoleEnum role;

    public SignupDto(ReqSignupDto reqSignupDto) {
        this.username = reqSignupDto.getUsername();
        this.password = reqSignupDto.getPassword();
        this.email = reqSignupDto.getEmail();
        this.name = reqSignupDto.getName();
        this.githubId = reqSignupDto.getGithubId();
        this.studentNumber = reqSignupDto.getStudentNumber();
        this.term = reqSignupDto.getTerm();
        this.role = UserRoleEnum.USER;
    }

    public static SignupDto from(ReqSignupDto reqSignupDto) {
        return new SignupDto(reqSignupDto);
    }
}
