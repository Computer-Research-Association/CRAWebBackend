package com.handong.cra.crawebbackend.project.dto.response;

import com.handong.cra.crawebbackend.project.dto.UpdateProjectDto;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResUpdateProjectDto {
    private Long id;
    private String semester;
    private String teamName;
    private String serviceName;
    private String content;
    private String gitHubUrl;
    private String serviceUrl;
    private List<String> members = new ArrayList<>();
    private List<String> imageUrls = new ArrayList<>();

    private Boolean deleted;
    private LocalDateTime createAt;
    private LocalDateTime updatedAt;

    public ResUpdateProjectDto(UpdateProjectDto updateProjectDto) {
        this.id = updateProjectDto.getId();
        this.semester = updateProjectDto.getSemester();
        this.teamName = updateProjectDto.getTeamName();
        this.serviceName = updateProjectDto.getServiceName();
        this.content = updateProjectDto.getContent();
        this.gitHubUrl = updateProjectDto.getGitHubUrl();
        this.serviceUrl = updateProjectDto.getServiceUrl();
        this.members = updateProjectDto.getMembers();
        this.imageUrls = updateProjectDto.getImageUrls();
        this.deleted = updateProjectDto.getDeleted();
        this.createAt = updateProjectDto.getCreateAt();
        this.updatedAt = updateProjectDto.getUpdatedAt();
    }

    public static ResUpdateProjectDto from(UpdateProjectDto updateProjectDto){
        return new ResUpdateProjectDto(updateProjectDto);
    }
}
