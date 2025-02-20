package com.handong.cra.crawebbackend.user.dto;

import com.handong.cra.crawebbackend.user.domain.User;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailDto {
    private Long id;
    private String name;
    private String email;
    private String studentId;
    private String term;
    private String githubId;
    private String imgUrl;
    private String greetingMessage;
    private Boolean deleted;

    public UserDetailDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.studentId = user.getStudentId();
        this.term = user.getTerm();
        this.githubId = user.getGithubId();
        this.imgUrl = user.getImgUrl();
        this.greetingMessage = user.getGreetingMessage();
        this.deleted = user.getDeleted();
    }

    public static UserDetailDto from(User user){
        return new UserDetailDto(user);
    }

}
