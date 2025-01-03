package com.handong.cra.crawebbackend.project.controller;


import com.handong.cra.crawebbackend.project.dto.CreateProjectDto;
import com.handong.cra.crawebbackend.project.dto.request.ReqCreateProjectDto;
import com.handong.cra.crawebbackend.project.dto.request.ReqUpdateProjectDto;
import com.handong.cra.crawebbackend.project.dto.response.ResCreateProjectDto;
import com.handong.cra.crawebbackend.project.dto.response.ResUpdateProjectDto;
import com.handong.cra.crawebbackend.project.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/project")
public class AdminProjectController {

    private final ProjectService projectService;


    @PostMapping("")
    public ResponseEntity<ResCreateProjectDto> createProject(@RequestBody ReqCreateProjectDto reqCreateProjectDto) {
        CreateProjectDto createProjectDto = projectService.createProject(CreateProjectDto.from(reqCreateProjectDto));
        ResCreateProjectDto resCreateProjectDto = ResCreateProjectDto.of(createProjectDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(resCreateProjectDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResUpdateProjectDto> updateProjectById(@PathVariable Long id, @RequestBody ReqUpdateProjectDto reqUpdateProjectDto) {
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProjectById(@PathVariable Long id) {
        return null;
    }

}
