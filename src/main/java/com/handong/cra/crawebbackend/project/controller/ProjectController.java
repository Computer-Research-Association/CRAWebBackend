package com.handong.cra.crawebbackend.project.controller;

import com.handong.cra.crawebbackend.board.domain.BoardOrderBy;
import com.handong.cra.crawebbackend.project.domain.ProjectOrderBy;
import com.handong.cra.crawebbackend.project.dto.response.ResDetailProjectDto;
import com.handong.cra.crawebbackend.project.dto.response.ResListProjectDto;
import com.handong.cra.crawebbackend.project.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/project")
public class ProjectController {

    private final ProjectService projectService;

    // all
    @GetMapping("")
    public ResponseEntity<List<ResListProjectDto>> getAllProjects() {
        return ResponseEntity.ok(projectService.getListProject()
                .stream().map(ResListProjectDto::from).filter(Objects::nonNull).toList());
    }


    // pagenation
    @GetMapping("/list/{page}")
    public ResponseEntity<List<ResListProjectDto>> getPageListProject(
            @PathVariable Long page,
            @RequestParam(required = false, defaultValue = "0") Integer perPage,
            @RequestParam(required = false, defaultValue = "5") Integer orderBy,
            @RequestParam(required = false, defaultValue = "true") Boolean isASC
    ) {
        return ResponseEntity.ok(projectService.getPaginationProject(page, perPage, ProjectOrderBy.values()[orderBy], isASC)
                .stream().map(ResListProjectDto::from).filter(Objects::nonNull).toList());
    }

    @GetMapping("/view/{id}")
    public ResponseEntity<ResDetailProjectDto> getDetailProject(@PathVariable Long id) {
        ResDetailProjectDto resDetailProjectDto = ResDetailProjectDto.from(projectService.getDetailProjectById(id));
        // 삭제되었거나 없는 경우
        if (resDetailProjectDto == null) return ResponseEntity.notFound().build();
        else return ResponseEntity.ok(resDetailProjectDto);
    }
}
