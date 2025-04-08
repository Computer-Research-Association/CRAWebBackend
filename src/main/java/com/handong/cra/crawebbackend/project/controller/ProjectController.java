package com.handong.cra.crawebbackend.project.controller;

import com.handong.cra.crawebbackend.exception.project.ProjectNotFoundException;
import com.handong.cra.crawebbackend.exception.project.ProjectPageSizeLimitExceededException;
import com.handong.cra.crawebbackend.project.domain.ProjectOrderBy;
import com.handong.cra.crawebbackend.project.dto.PageProjectDataDto;
import com.handong.cra.crawebbackend.project.dto.response.ResDetailProjectDto;
import com.handong.cra.crawebbackend.project.dto.response.ResPageProjectDto;
import com.handong.cra.crawebbackend.project.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/project")
public class ProjectController {
    private final ProjectService projectService;

    @Value("${spring.data.page.MAX_PER_PAGE}")
    private Integer MAX_PAGE_SIZE;


    @GetMapping("/list/{page}") // 프로젝트 pagenation
    @Operation(summary = "프로젝트 페이지 조회")
    public ResponseEntity<ResPageProjectDto> getPageListProject(
            @PathVariable final Long page,
            @RequestParam(required = false, defaultValue = "5") final Integer perPage,
            @RequestParam(required = false, defaultValue = "0") final Integer orderBy,
            @RequestParam(required = false, defaultValue = "true") final Boolean isASC
    ) {
        if (perPage > MAX_PAGE_SIZE) {
            throw new ProjectPageSizeLimitExceededException();
        }
        final PageProjectDataDto pageProjectDataDto = PageProjectDataDto.builder()
                .page(page)
                .perPage(perPage)
                .isASC(isASC)
                .projectOrderBy(ProjectOrderBy.values()[orderBy])
                .build();
        return ResponseEntity.ok(ResPageProjectDto.from(projectService.getPaginationProject(pageProjectDataDto)));
    }

    @GetMapping("/view/{projectId}") // 프로젝트 정보 가져오기
    @Operation(summary = "프로젝트 조회", description = "아이디로 프로젝트 조회")
    public ResponseEntity<ResDetailProjectDto> getDetailProject(@PathVariable final Long projectId) {
        final ResDetailProjectDto resDetailProjectDto = ResDetailProjectDto.from(projectService.getDetailProjectById(projectId));
        // 삭제되었거나 없는 경우
        if (resDetailProjectDto == null) {
            throw new ProjectNotFoundException();
        }
        return ResponseEntity.ok(resDetailProjectDto);
    }
}
