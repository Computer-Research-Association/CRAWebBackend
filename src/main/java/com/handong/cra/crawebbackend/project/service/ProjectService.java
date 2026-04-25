package com.handong.cra.crawebbackend.project.service;

import com.handong.cra.crawebbackend.project.dto.*;

import java.util.List;


public interface ProjectService {
    /**
     * 프로젝트 생성
     *
     * @param createProjectDto 프로젝트 생생 데이터 DTO
     */
    CreateProjectDto createProject(CreateProjectDto createProjectDto);

    /**
     * 프로젝트 수정
     *
     * @param updateProjectDto 수정할 프로젝트 데이터 DTO
     */
    UpdateProjectDto updateProject(UpdateProjectDto updateProjectDto);

    /**
     * 프로젝트 삭제
     *
     * @param updateProjectDto 삭제할 프로젝트 데이터 DTO
     */
    Boolean deleteProjectById(UpdateProjectDto updateProjectDto);

    /**
     * 프로젝트 정보 반환
     *
     * @param projectId 확인할 프로젝트 아이디
     */
    DetailProjectDto getDetailProjectById(Long projectId);

    /**
     * 모든 프로젝트 리스트 반환
     */
    List<ListProjectDto> getListProject();

    /**
     * 프로젝트 페이지로 반환
     *
     * @param pageProjectDataDto 페이지 데이터 DTO
     */
    PageProjectDto getPaginationProject(PageProjectDataDto pageProjectDataDto);

    /**
     * 태그로 프로젝트 목록 조회
     *
     * @param tagName 태그 이름
     */
    List<ListProjectDto> getProjectsByTagName(String tagName);
}
