package com.handong.cra.crawebbackend.project.controller;

import com.handong.cra.crawebbackend.board.domain.BoardOrderBy;
import com.handong.cra.crawebbackend.exception.project.ProjectNotFoundException;
import com.handong.cra.crawebbackend.exception.project.ProjectPageSizeLimitExceededException;
import com.handong.cra.crawebbackend.project.domain.ProjectOrderBy;
import com.handong.cra.crawebbackend.project.dto.PageProjectDataDto;
import com.handong.cra.crawebbackend.project.dto.response.ResDetailProjectDto;
import com.handong.cra.crawebbackend.project.dto.response.ResListProjectDto;
import com.handong.cra.crawebbackend.project.dto.response.ResPageProjectDto;
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
    private final Integer MAX_PAGE_SIZE = 100;

//    // all
//    @GetMapping("")
//    public ResponseEntity<List<ResListProjectDto>> getAllProjects() {
//        return ResponseEntity.ok(projectService.getListProject()
//                .stream().map(ResListProjectDto::from).filter(Objects::nonNull).toList());
//    }


    // pagenation
    @GetMapping("/list/{page}")
    public ResponseEntity<ResPageProjectDto> getPageListProject(
            @PathVariable Long page,
            @RequestParam(required = false, defaultValue = "5") Integer perPage,
            @RequestParam(required = false, defaultValue = "0") Integer orderBy,
            @RequestParam(required = false, defaultValue = "true") Boolean isASC
    ) {
        if (perPage > MAX_PAGE_SIZE) {
            throw new ProjectPageSizeLimitExceededException();
        }
        PageProjectDataDto pageProjectDataDto = PageProjectDataDto.builder()
                .page(page)
                .perPage(perPage)
                .isASC(isASC)
                .projectOrderBy(ProjectOrderBy.values()[orderBy])
                .build();

        return ResponseEntity.ok(ResPageProjectDto.from(projectService.getPaginationProject(pageProjectDataDto)));
    }

    @GetMapping("/view/{id}")
    public ResponseEntity<ResDetailProjectDto> getDetailProject(@PathVariable Long id) {
        ResDetailProjectDto resDetailProjectDto = ResDetailProjectDto.from(projectService.getDetailProjectById(id));
        // 삭제되었거나 없는 경우
        if (resDetailProjectDto == null) throw new ProjectNotFoundException();
        else return ResponseEntity.ok(resDetailProjectDto);
    }
}
