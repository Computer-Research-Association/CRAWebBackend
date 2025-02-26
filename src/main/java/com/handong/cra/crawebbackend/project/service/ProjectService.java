package com.handong.cra.crawebbackend.project.service;

import com.handong.cra.crawebbackend.board.domain.BoardOrderBy;
import com.handong.cra.crawebbackend.project.domain.Project;
import com.handong.cra.crawebbackend.project.domain.ProjectOrderBy;
import com.handong.cra.crawebbackend.project.dto.*;

import java.util.List;


public interface ProjectService {
    // admin
    public CreateProjectDto createProject(CreateProjectDto createProjectDto);

    public UpdateProjectDto updateProject(UpdateProjectDto updateProjectDto);

    public Boolean deleteProjectById(UpdateProjectDto updateProjectDto);

    // users
    public DetailProjectDto getDetailProjectById(Long id);

    public List<ListProjectDto> getListProject();

    public PageProjectDto getPaginationProject(PageProjectDataDto pageProjectDataDto);
}
