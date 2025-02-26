package com.handong.cra.crawebbackend.user.dto;

import com.handong.cra.crawebbackend.user.domain.User;
import com.handong.cra.crawebbackend.user.dto.request.ReqUpdateUserDto;
import com.handong.cra.crawebbackend.user.dto.response.ResUpdateUserDto;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UpdateUserDto {
    private Long id;
    private String name;
    private String email;
    private String studentId;
    private String term;
    private String githubId;
    private String greetingMessage;
    private Boolean deleted;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    public UpdateUserDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.studentId = user.getStudentId();
        this.term = user.getTerm();
        this.githubId = user.getGithubId();
        this.greetingMessage = user.getGreetingMessage();
        this.createdAt = user.getCreatedAt();
        this.updatedAt = user.getUpdatedAt();
    }

    public UpdateUserDto(Long userId, ReqUpdateUserDto reqUpdateUserDto) {
        this.id = userId;
        this.name = reqUpdateUserDto.getName();
        this.email = reqUpdateUserDto.getEmail();
        this.studentId = reqUpdateUserDto.getStudentId();
        this.term = reqUpdateUserDto.getTerm();
        this.githubId = reqUpdateUserDto.getGithubId();
        this.greetingMessage = reqUpdateUserDto.getGreetingMessage();
    }

    public UpdateUserDto(Long userId, Boolean deleted) {
        this.id = userId;
        this.deleted = deleted;
    }

    public static UpdateUserDto from(User user) {
        return new UpdateUserDto(user);
    }

    public static UpdateUserDto of(Long userId, ReqUpdateUserDto reqUpdateUserDto) {
        return new UpdateUserDto(userId, reqUpdateUserDto);
    }

    public static UpdateUserDto of(Long userId, boolean deleted) {
        return new UpdateUserDto(userId, deleted);
    }
}
