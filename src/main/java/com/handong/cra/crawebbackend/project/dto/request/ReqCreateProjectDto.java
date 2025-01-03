package com.handong.cra.crawebbackend.project.dto.request;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ReqCreateProjectDto {
    private Integer year;
    private Integer semester;
    private String teamName;
    private String serviceName;
    private String content;
    private String gitHubUrl;
    private String serviceUrl;
    private List<String> members = new ArrayList<>();
    private List<String> imageUrls = new ArrayList<>(); // 이후 multipart 로 변경 해야함.
}
