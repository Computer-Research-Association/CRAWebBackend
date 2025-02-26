package com.handong.cra.crawebbackend.project.dto.request;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ReqCreateProjectDto {
    private String semester;
    private String teamName;
    private String serviceName;
    private String content;
    private String gitHubUrl;
    private String serviceUrl;
    private List<String> members = new ArrayList<>();
    private String imageUrl;
}
