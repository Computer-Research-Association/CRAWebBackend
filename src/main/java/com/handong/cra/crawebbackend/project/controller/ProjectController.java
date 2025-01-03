package com.handong.cra.crawebbackend.project.controller;


import com.handong.cra.crawebbackend.project.dto.request.ReqCreateProjectDto;
import com.handong.cra.crawebbackend.project.dto.response.ResCreateProjectDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/project")
public class ProjectController {

    @PostMapping("")
    public ResponseEntity<ResCreateProjectDto> createProject(@RequestBody ReqCreateProjectDto reqCreateProjectDto){




    }
}
