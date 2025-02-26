package com.handong.cra.crawebbackend.project.service;

import com.handong.cra.crawebbackend.board.domain.BoardOrderBy;
import com.handong.cra.crawebbackend.project.domain.Project;
import com.handong.cra.crawebbackend.project.domain.ProjectOrderBy;
import com.handong.cra.crawebbackend.project.dto.CreateProjectDto;
import com.handong.cra.crawebbackend.project.dto.DetailProjectDto;
import com.handong.cra.crawebbackend.project.dto.ListProjectDto;
import com.handong.cra.crawebbackend.project.dto.UpdateProjectDto;

import java.util.List;


public interface ProjectService {
    // admin
    public CreateProjectDto createProject(CreateProjectDto createProjectDto);

    public UpdateProjectDto updateProject(UpdateProjectDto updateProjectDto);

    public Boolean deleteProjectById(UpdateProjectDto updateProjectDto);

    // users
    public DetailProjectDto getDetailProjectById(Long id);

    public List<ListProjectDto> getListProject();

    public List<ListProjectDto> getPaginationProject(Long page, Integer perPage, ProjectOrderBy orderBy, Boolean isASC);
}
