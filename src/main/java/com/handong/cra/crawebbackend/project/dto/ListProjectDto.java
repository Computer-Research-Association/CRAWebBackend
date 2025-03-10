package com.handong.cra.crawebbackend.project.dto;

import com.handong.cra.crawebbackend.item.domain.ItemCategory;
import com.handong.cra.crawebbackend.project.domain.Project;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ListProjectDto {
    private Long id;
    private String semester;
    private String teamName;
    private String serviceName;
//    private String content; // TODO 디자인 보고 결정
    private List<String> members = new ArrayList<>();
    private String imageUrl = "";

    private Boolean deleted;
    private LocalDateTime createAt;

    public ListProjectDto (Project project){
        this.id =project.getId();
        this.semester = project.getSemester();
        this.teamName = project.getTeamName();
        this.serviceName = project.getServiceName();
//        this.content = project.getContent();
        this.members = project.getMembers();
        this.imageUrl = project.getImageUrl();
        this.deleted = project.getDeleted();
        this.createAt = project.getCreatedAt();
    }

    public static ListProjectDto from(Project project){
        return new ListProjectDto(project);
    }

    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Builder
    public static class PageItemDataDto {
        private Long page;
        private Integer perPage;
        private Boolean isASC;
        private ItemCategory itemCategory;
    }
}
