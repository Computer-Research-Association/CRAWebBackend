package com.handong.cra.crawebbackend.project.controller;


import com.handong.cra.crawebbackend.auth.domain.CustomUserDetails;
import com.handong.cra.crawebbackend.project.dto.CreateProjectDto;
import com.handong.cra.crawebbackend.project.dto.UpdateProjectDto;
import com.handong.cra.crawebbackend.project.dto.request.ReqCreateProjectDto;
import com.handong.cra.crawebbackend.project.dto.request.ReqUpdateProjectDto;
import com.handong.cra.crawebbackend.project.dto.response.ResCreateProjectDto;
import com.handong.cra.crawebbackend.project.dto.response.ResUpdateProjectDto;
import com.handong.cra.crawebbackend.project.service.ProjectService;
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


    @PostMapping("")
    public ResponseEntity<ResCreateProjectDto> createProject(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody ReqCreateProjectDto reqCreateProjectDto) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResCreateProjectDto.of(projectService.createProject(CreateProjectDto.of(customUserDetails.getUserId(), reqCreateProjectDto))));
    }

    @PutMapping("/{projectId}")
    public ResponseEntity<ResUpdateProjectDto> updateProjectById(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable Long projectId, @RequestBody ReqUpdateProjectDto reqUpdateProjectDto) {
        ResUpdateProjectDto resUpdateProjectDto = ResUpdateProjectDto.from(projectService.updateProject(UpdateProjectDto.of(projectId,customUserDetails.getUserId(), reqUpdateProjectDto)));
        return ResponseEntity.ok(resUpdateProjectDto);
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<Void> deleteProjectById(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable Long projectId) {
        if (projectService.deleteProjectById(UpdateProjectDto.of(projectId,customUserDetails.getUserId()))) return ResponseEntity.ok().build();
        else return ResponseEntity.internalServerError().build();
    }

}
