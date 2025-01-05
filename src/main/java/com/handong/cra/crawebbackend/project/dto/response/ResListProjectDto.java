package com.handong.cra.crawebbackend.project.dto.response;

import com.handong.cra.crawebbackend.project.domain.Project;
import com.handong.cra.crawebbackend.project.dto.ListProjectDto;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResListProjectDto {
    private Long id;
    private String semester;
    private String teamName;
    private String serviceName;
    //    private String content; // TODO 디자인 보고 결정
    private List<String> members = new ArrayList<>();
    private String imageUrl = "";

    private Boolean deleted;
    private LocalDateTime createAt;


    public ResListProjectDto(ListProjectDto listProjectDto) {
        this.id = listProjectDto.getId();
        this.semester = listProjectDto.getSemester();
        this.teamName = listProjectDto.getTeamName();
        this.serviceName = listProjectDto.getServiceName();
//        this.content = listProjectDto.getContent();
        this.members = listProjectDto.getMembers();
        this.imageUrl = listProjectDto.getImageUrl(); // 대표 이미지만 가져오기
        this.deleted = listProjectDto.getDeleted();
        this.createAt = listProjectDto.getCreateAt();
    }

    public static ResListProjectDto from(ListProjectDto listProjectDto) {
        if (listProjectDto.getDeleted()) return null;
        else return new ResListProjectDto(listProjectDto);
    }
}
