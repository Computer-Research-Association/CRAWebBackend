package com.handong.cra.crawebbackend.project.dto;

import com.handong.cra.crawebbackend.project.domain.Project;
import com.handong.cra.crawebbackend.project.dto.request.ReqUpdateProjectDto;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UpdateProjectDto {
    private Long id;
    private Long userId;
    private String semester;
    private String teamName;
    private String serviceName;
    private String content;
    private String gitHubUrl;
    private String serviceUrl;
    private List<String> members = new ArrayList<>();
    private String imageUrl;

    private Boolean deleted;
    private LocalDateTime createAt = null;
    private LocalDateTime updatedAt = null;

    public UpdateProjectDto(Long id, Long userId, ReqUpdateProjectDto reqUpdateProjectDto) {
        this.id = id;
        this.userId = userId;
        this.semester = reqUpdateProjectDto.getSemester();
        this.teamName = reqUpdateProjectDto.getTeamName();
        this.serviceName = reqUpdateProjectDto.getServiceName();
        this.content = reqUpdateProjectDto.getContent();
        this.gitHubUrl = reqUpdateProjectDto.getGitHubUrl();
        this.serviceUrl = reqUpdateProjectDto.getServiceUrl();
        this.members = reqUpdateProjectDto.getMembers();
        this.imageUrl = reqUpdateProjectDto.getImageUrl();
        this.deleted = reqUpdateProjectDto.getDeleted();
    }

    public UpdateProjectDto(Project project) {
        this.id = project.getId();
        this.semester = project.getSemester();
        this.teamName = project.getTeamName();
        this.serviceName = project.getServiceName();
        this.content = project.getContent();
        this.gitHubUrl = project.getGitHubUrl();
        this.serviceUrl = project.getServiceUrl();
        this.members = project.getMembers();
        this.imageUrl = project.getImageUrl();
        this.deleted = project.getDeleted();
    }

    public UpdateProjectDto(Long id, Long userId) {
        this.id = id;
        this.userId = userId;
        this.deleted = true;
    }

    public static UpdateProjectDto of(Long id, Long userId, ReqUpdateProjectDto reqUpdateProjectDto) {
        return new UpdateProjectDto(id, userId, reqUpdateProjectDto);
    }
    public static UpdateProjectDto of(Long id, Long userId) {
        return new UpdateProjectDto(id, userId);
    }

    public static UpdateProjectDto from(Project project) {
        return new UpdateProjectDto(project);
    }
}
