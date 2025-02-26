package com.handong.cra.crawebbackend.project.dto.response;

import com.handong.cra.crawebbackend.board.dto.response.ResDetailBoardDto;
import com.handong.cra.crawebbackend.project.dto.DetailProjectDto;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ResDetailProjectDto {
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
    private LocalDateTime createAt;
    private LocalDateTime updatedAt;

    public ResDetailProjectDto(DetailProjectDto detailProjectDto){
        this.id = detailProjectDto.getId();
        this.semester = detailProjectDto.getSemester();
        this.teamName = detailProjectDto.getTeamName();
        this.serviceName = detailProjectDto.getServiceName();
        this.content = detailProjectDto.getContent();
        this.gitHubUrl = detailProjectDto.getGitHubUrl();
        this.serviceUrl = detailProjectDto.getServiceUrl();
        this.members = detailProjectDto.getMembers();
        this.imageUrl =detailProjectDto.getImageUrl();
        this.deleted = false;
        this.createAt = detailProjectDto.getCreateAt();
        this.updatedAt = detailProjectDto.getUpdatedAt();
    }

    public static ResDetailProjectDto from(DetailProjectDto detailProjectDto){
        if (detailProjectDto == null) return null;
        else return new ResDetailProjectDto(detailProjectDto);
    }
}
