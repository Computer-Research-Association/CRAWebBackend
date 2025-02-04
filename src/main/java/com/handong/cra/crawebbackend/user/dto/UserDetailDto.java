package com.handong.cra.crawebbackend.user.dto;

import com.handong.cra.crawebbackend.user.domain.User;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserDetailDto {
    private String name;
    private String email;
    private Long studentId;
    private String term;
    private String githubId;
    private String imgUrl;

    public UserDetailDto(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.studentId = user.getStudentId();
        this.term = user.getTerm();
        this.githubId = user.getGithubId();
        this.imgUrl = user.getImgUrl();
    }


    public static UserDetailDto from(User user){
        return new UserDetailDto(user);
    }

}
