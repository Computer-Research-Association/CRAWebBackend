package com.handong.cra.crawebbackend.user.dto.response;


import com.handong.cra.crawebbackend.user.dto.UserDetailDto;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResUserDetailDto {
    private String name;
    private String term;
    private String githubId;
    private String greetingMessage;
    private Long studentNumber;


    public ResUserDetailDto(UserDetailDto userDetailDto){
//        this.name = userDetailDto
    }


    // 기수 이름, 학번 한마디 깃헙주소
}
