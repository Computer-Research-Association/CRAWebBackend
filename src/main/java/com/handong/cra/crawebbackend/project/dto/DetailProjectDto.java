package com.handong.cra.crawebbackend.project.dto;

import com.handong.cra.crawebbackend.project.domain.Project;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DetailProjectDto {
    private Long id;
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


    public  DetailProjectDto(Project project){
        this.id = project.getId();
        this.semester = project.getSemester();
        this.teamName = project.getTeamName();
        this.serviceName = project.getServiceName();;
        this.content = project.getContent();
        this.gitHubUrl = project.getGitHubUrl();
        this.serviceUrl = project.getServiceUrl();
        this.members = project.getMembers();
        this.imageUrl =project.getImageUrls();
        this.deleted = project.getDeleted();
        this.createAt = project.getCreatedAt();
        this.updatedAt = project.getUpdatedAt();
    }
    public static DetailProjectDto from(Project project) {
        return new DetailProjectDto(project);
    }
}
