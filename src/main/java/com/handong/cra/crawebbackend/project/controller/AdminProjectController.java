package com.handong.cra.crawebbackend.project.controller;


import com.handong.cra.crawebbackend.project.dto.request.ReqCreateProjectDto;
import com.handong.cra.crawebbackend.project.dto.request.ReqUpdateProjectDto;
import com.handong.cra.crawebbackend.project.dto.response.ResCreateProjectDto;
import com.handong.cra.crawebbackend.project.dto.response.ResUpdateProjectDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/project")
public class AdminProjectController {

    @PostMapping("")
    public ResponseEntity<ResCreateProjectDto> createProject(@RequestBody ReqCreateProjectDto reqCreateProjectDto) {
        return null;

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
