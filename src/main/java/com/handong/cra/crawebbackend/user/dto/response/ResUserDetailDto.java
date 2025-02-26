package com.handong.cra.crawebbackend.user.dto.response;


import com.handong.cra.crawebbackend.user.dto.UpdateUserDto;
import com.handong.cra.crawebbackend.user.dto.UserDetailDto;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResUserDetailDto {
    private String name;
    private String email;
    private String studentId;
    private String term;
    private String githubId;
    private String greetingMessage;
    private String imgUrl;


    public ResUserDetailDto(UserDetailDto userDetailDto){
        this.name = userDetailDto.getName();
        this.email = userDetailDto.getEmail();
        this.studentId = userDetailDto.getStudentId();
        this.term = userDetailDto.getTerm();
        this.githubId = userDetailDto.getGithubId();
        this.greetingMessage = userDetailDto.getGreetingMessage();
        this.imgUrl = userDetailDto.getImgUrl();
    }

    public static ResUserDetailDto from (UserDetailDto userDetailDto){
        return new ResUserDetailDto(userDetailDto);
    }
}
