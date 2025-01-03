package com.handong.cra.crawebbackend.project.domain;

import com.handong.cra.crawebbackend.common.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Project extends BaseEntity {

    @Column(name = "team_name", length = 150)
    private String teamName;

    @Column(name = "project_name", length = 150)
    private String projectName;

    @Column(length = 2000)
    private String content;

    @Column(name = "github_url")
    private String gitHubUrl;

    @Column(name = "service_url")
    private String serviceUrl;

    @CollectionTable(name = "member_name", joinColumns = @JoinColumn(name = "project_name"))
    @ElementCollection
    private List<String> members; // 이름만 저장

    @ElementCollection
    @CollectionTable(name = "project_images", joinColumns = @JoinColumn(name = "project_id"))
    @Column(name = "image_url")
    private List<String> imageUrls = new ArrayList<>();





}
