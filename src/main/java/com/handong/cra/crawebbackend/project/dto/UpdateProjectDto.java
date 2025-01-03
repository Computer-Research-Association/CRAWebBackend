package com.handong.cra.crawebbackend.project.dto;

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
    private Integer year;
    private Integer semester;
    private String teamName;
    private String serviceName;
    private String content;
    private String gitHubUrl;
    private String serviceUrl;
    private List<String> members = new ArrayList<>();
    private List<String> imageUrls = new ArrayList<>();

    private Boolean deleted;
    private LocalDateTime createAt = null;
    private LocalDateTime updatedAt = null;

    public UpdateProjectDto(Long id, ReqUpdateProjectDto reqUpdateProjectDto) {
        this.id = id;
        this.year = reqUpdateProjectDto.getYear();
        this.semester = reqUpdateProjectDto.getSemester();
        this.teamName = reqUpdateProjectDto.getTeamName();
        this.serviceName = reqUpdateProjectDto.getServiceName();
        this.content = reqUpdateProjectDto.getContent();
        this.gitHubUrl = reqUpdateProjectDto.getGitHubUrl();
        this.serviceUrl = reqUpdateProjectDto.getServiceUrl();
        this.members = reqUpdateProjectDto.getMembers();
        this.imageUrls = reqUpdateProjectDto.getImageUrls();
        this.deleted = reqUpdateProjectDto.getDeleted();
    }

    public static UpdateProjectDto of(Long id, ReqUpdateProjectDto reqUpdateProjectDto) {
        return new UpdateProjectDto(id, reqUpdateProjectDto);
    }
}
