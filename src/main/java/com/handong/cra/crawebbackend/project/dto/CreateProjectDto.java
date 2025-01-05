package com.handong.cra.crawebbackend.project.dto;

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
    private List<String> imageUrls = new ArrayList<>();

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
        this.imageUrls = reqCreateProjectDto.getImageUrls();
    }

    public static CreateProjectDto from(ReqCreateProjectDto reqCreateProjectDto) {
        return new CreateProjectDto(reqCreateProjectDto);
    }
}
