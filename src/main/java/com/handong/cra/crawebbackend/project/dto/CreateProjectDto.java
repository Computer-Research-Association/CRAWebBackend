package com.handong.cra.crawebbackend.project.dto;

import com.handong.cra.crawebbackend.project.domain.Project;
import com.handong.cra.crawebbackend.project.dto.request.ReqCreateProjectDto;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateProjectDto {
    private String semester;
    private String teamName;
    private String serviceName;
    private String content;
    private String gitHubUrl;
    private String serviceUrl;
    private List<String> members = new ArrayList<>();
    private String imageUrl;

    //res 전달용
    private Long id = null;
    private LocalDateTime createdAt = null;

    public CreateProjectDto(ReqCreateProjectDto reqCreateProjectDto) {
        this.semester = reqCreateProjectDto.getSemester();
        this.teamName = reqCreateProjectDto.getTeamName();
        this.serviceName = reqCreateProjectDto.getServiceName();
        this.content = reqCreateProjectDto.getContent();
        this.gitHubUrl = reqCreateProjectDto.getGitHubUrl();
        this.serviceUrl = reqCreateProjectDto.getServiceUrl();
        this.members = reqCreateProjectDto.getMembers();
        this.imageUrl = reqCreateProjectDto.getImageUrl();
    }
    public CreateProjectDto(Project project) {
        this.semester = project.getSemester();
        this.teamName = project.getTeamName();
        this.serviceName = project.getServiceName();
        this.content = project.getContent();
        this.gitHubUrl = project.getGitHubUrl();
        this.serviceUrl = project.getServiceUrl();
        this.members = project.getMembers();
        this.imageUrl = project.getImageUrl();
    }

    public static CreateProjectDto from(ReqCreateProjectDto reqCreateProjectDto) {
        return new CreateProjectDto(reqCreateProjectDto);
    }

    public static CreateProjectDto from(Project project) {
        return new CreateProjectDto(project);
    }
}
