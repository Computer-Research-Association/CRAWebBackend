package com.handong.cra.crawebbackend.project.controller;


import com.handong.cra.crawebbackend.auth.domain.CustomUserDetails;
import com.handong.cra.crawebbackend.project.dto.CreateProjectDto;
import com.handong.cra.crawebbackend.project.dto.UpdateProjectDto;
import com.handong.cra.crawebbackend.project.dto.request.ReqCreateProjectDto;
import com.handong.cra.crawebbackend.project.dto.request.ReqUpdateProjectDto;
import com.handong.cra.crawebbackend.project.dto.response.ResCreateProjectDto;
import com.handong.cra.crawebbackend.project.dto.response.ResUpdateProjectDto;
import com.handong.cra.crawebbackend.project.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/project")
public class AdminProjectController {
    private final ProjectService projectService;


    @PostMapping("") // 새로운 프로젝트 데이터 생성
    @Operation(summary = "프로젝트 생성")
    public ResponseEntity<ResCreateProjectDto> createProject(
            @AuthenticationPrincipal final CustomUserDetails customUserDetails,
            @RequestBody final ReqCreateProjectDto reqCreateProjectDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResCreateProjectDto.of(projectService.createProject(
                        CreateProjectDto.of(customUserDetails.getUserId(), reqCreateProjectDto))));
    }

    @PutMapping("/{projectId}") // 기존 프로젝트 데이터 수정
    @Operation(summary = "기존 프로젝트 수정")
    public ResponseEntity<ResUpdateProjectDto> updateProjectById(
            @AuthenticationPrincipal final CustomUserDetails customUserDetails,
            @PathVariable final Long projectId,
            @RequestBody final ReqUpdateProjectDto reqUpdateProjectDto) {
        return ResponseEntity.ok(ResUpdateProjectDto.from(projectService.updateProject(
                UpdateProjectDto.of(projectId, customUserDetails.getUserId(), reqUpdateProjectDto))));
    }

    @DeleteMapping("/{projectId}") // 기존 프로젝트 데이터 삭제
    @Operation(summary = "프로젝트 삭제")
    public ResponseEntity<Void> deleteProjectById(
            @AuthenticationPrincipal final CustomUserDetails customUserDetails,
            @PathVariable final Long projectId) {
        projectService.deleteProjectById(UpdateProjectDto.of(projectId, customUserDetails.getUserId()));
        return ResponseEntity.ok().build();
    }

}
