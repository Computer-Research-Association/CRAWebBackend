package com.handong.cra.crawebbackend.project.dto.response;


import com.handong.cra.crawebbackend.project.dto.CreateProjectDto;
import com.handong.cra.crawebbackend.tag.dto.response.ResTagDto;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ResCreateProjectDto {
    private Long id;
    private String semester;
    private String teamName;
    private String serviceName;
    private String content;
    private String gitHubUrl;
    private String serviceUrl;
    private List<String> members = new ArrayList<>();
    private String imageUrl;
    private List<ResTagDto> tags;

    private Boolean deleted;
    private LocalDateTime createdAt;

    public ResCreateProjectDto(CreateProjectDto createProjectDto) {
        this.id = createProjectDto.getId();
        this.semester = createProjectDto.getSemester();
        this.teamName = createProjectDto.getTeamName();
        this.serviceName = createProjectDto.getServiceName();
        this.content = createProjectDto.getContent();
        this.gitHubUrl = createProjectDto.getGitHubUrl();
        this.serviceUrl = createProjectDto.getServiceUrl();
        this.members = createProjectDto.getMembers();
        this.imageUrl = createProjectDto.getImageUrl();
        this.tags = createProjectDto.getTags();
        this.deleted = false;
        this.createdAt = createProjectDto.getCreatedAt();
    }

    public static ResCreateProjectDto of(CreateProjectDto createProjectDto) {
        return new ResCreateProjectDto(createProjectDto);
    }
}
