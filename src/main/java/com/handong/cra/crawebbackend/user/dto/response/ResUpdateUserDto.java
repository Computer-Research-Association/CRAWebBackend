package com.handong.cra.crawebbackend.user.dto.response;

import com.handong.cra.crawebbackend.user.dto.UpdateUserDto;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ResUpdateUserDto {
    private String name;
    private String email;
    private String studentId;
    private String term;
    private String githubId;
    private Boolean deleted;
    private String greetingMessage;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    public ResUpdateUserDto(UpdateUserDto updateUserDto) {
        this.name = updateUserDto.getName();
        this.email = updateUserDto.getEmail();
        this.studentId = updateUserDto.getStudentId();
        this.term = updateUserDto.getTerm();
        this.githubId = updateUserDto.getGithubId();
        this.greetingMessage = updateUserDto.getGreetingMessage();
        this.createdAt = updateUserDto.getCreatedAt();
        this.updatedAt = updateUserDto.getUpdatedAt();
    }

    public static ResUpdateUserDto from(UpdateUserDto updateUserDto) {
        return new ResUpdateUserDto(updateUserDto);
    }


}
