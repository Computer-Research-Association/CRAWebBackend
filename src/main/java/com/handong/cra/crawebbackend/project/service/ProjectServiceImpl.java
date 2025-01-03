package com.handong.cra.crawebbackend.project.service;

import com.handong.cra.crawebbackend.board.domain.OrderBy;
import com.handong.cra.crawebbackend.project.domain.Project;
import com.handong.cra.crawebbackend.project.dto.CreateProjectDto;
import com.handong.cra.crawebbackend.project.dto.DetailProjectDto;
import com.handong.cra.crawebbackend.project.dto.ListProjectDto;
import com.handong.cra.crawebbackend.project.dto.UpdateProjectDto;
import com.handong.cra.crawebbackend.project.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {


    private final ProjectRepository projectRepository;

    @Override
    public CreateProjectDto createProject(CreateProjectDto createProjectDto) {
        Project project = Project.from(createProjectDto);
        project = projectRepository.save(project); // save 된 Entity 가져옴

        // set meta data
        createProjectDto.setCreatedAt(project.getCreatedAt());
        createProjectDto.setId(project.getId());

        return createProjectDto;
    }

    @Override
    public UpdateProjectDto updateProject(UpdateProjectDto updateProjectDto) {
        return null;
    }

    @Override
    public Boolean deleteProjectById(Long id) {
        return null;
    }

    @Override
    public DetailProjectDto getProjectById(Long id) {
        return null;
    }

    @Override
    public List<ListProjectDto> getListProject() {
        return List.of();
    }

    @Override
    public List<ListProjectDto> getPaginationProject(Long page, Integer perPage, OrderBy orderB, Boolean isASC) {
        return List.of();
    }
}
