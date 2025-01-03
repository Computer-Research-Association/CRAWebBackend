package com.handong.cra.crawebbackend.project.domain;

import com.handong.cra.crawebbackend.common.domain.BaseEntity;
import com.handong.cra.crawebbackend.project.dto.CreateProjectDto;
import com.handong.cra.crawebbackend.project.dto.UpdateProjectDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Project extends BaseEntity {

    @Column(length = 4)
    private Integer year;

    @Column(length = 1) // 1 or 2
    private Integer semester;

    @Column(name = "team_name", length = 150)
    private String teamName;

    @Column(name = "service_name", length = 150)
    private String serviceName;

    @Column(length = 2000)
    private String content;

    @Column(name = "github_url")
    private String gitHubUrl;

    @Column(name = "service_url")
    private String serviceUrl;

    @CollectionTable(name = "project_members", joinColumns = @JoinColumn(name = "project_id"))
    @ElementCollection
    private List<String> members; // 이름만 저장

    @ElementCollection
    @CollectionTable(name = "project_images", joinColumns = @JoinColumn(name = "project_id"))
    @Column(name = "image_url")
    private List<String> imageUrls = new ArrayList<>();


    public Project(CreateProjectDto createProjectDto) {
        this.year = createProjectDto.getYear();
        this.semester = createProjectDto.getSemester();
        this.teamName = createProjectDto.getTeamName();
        this.serviceName = createProjectDto.getServiceName();
        this.content = createProjectDto.getContent();
        this.gitHubUrl = createProjectDto.getGitHubUrl();
        this.serviceUrl = createProjectDto.getServiceUrl();
        this.members = createProjectDto.getMembers();
        this.imageUrls = createProjectDto.getImageUrls();
    }

    public static Project from(CreateProjectDto createProjectDto) {
        return new Project(createProjectDto);
    }


    public Project update(UpdateProjectDto updateProjectDto){
        this.year = updateProjectDto.getYear();
        this.semester = updateProjectDto.getSemester();
        this.teamName = updateProjectDto.getTeamName();
        this.serviceName = updateProjectDto.getServiceName();
        this.content = updateProjectDto.getContent();
        this.gitHubUrl = updateProjectDto.getGitHubUrl();
        this.serviceUrl = updateProjectDto.getServiceUrl();
        this.members = updateProjectDto.getMembers();
        this.imageUrls = updateProjectDto.getImageUrls();
        return this;
    }
}
