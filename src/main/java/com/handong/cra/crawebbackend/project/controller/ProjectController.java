package com.handong.cra.crawebbackend.project.controller;

import com.handong.cra.crawebbackend.project.dto.response.ResListProjectDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/project")
public class ProjectController {

    // all
    @GetMapping("")
    public ResponseEntity<List<ResListProjectDto>> getAllProjects(){
        return null;
    }


    // pagenation
    @GetMapping("/list/{page}")
    public ResponseEntity<List<ResListProjectDto>> getPageListProject(@PathVariable Long page){

        return null;

    }
}
