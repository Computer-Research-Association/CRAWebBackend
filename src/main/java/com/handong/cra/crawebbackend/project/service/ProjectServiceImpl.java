package com.handong.cra.crawebbackend.project.service;


import com.handong.cra.crawebbackend.exception.auth.AuthForbiddenActionException;
import com.handong.cra.crawebbackend.exception.project.ProjectSemesterParseException;
import com.handong.cra.crawebbackend.exception.user.UserNotFoundException;
import com.handong.cra.crawebbackend.file.domain.S3ImageCategory;
import com.handong.cra.crawebbackend.file.service.S3ImageService;
import com.handong.cra.crawebbackend.exception.project.ProjectNotFoundException;
import com.handong.cra.crawebbackend.project.domain.Project;
import com.handong.cra.crawebbackend.project.domain.ProjectOrderBy;
import com.handong.cra.crawebbackend.project.dto.*;
import com.handong.cra.crawebbackend.project.repository.ProjectRepository;
import com.handong.cra.crawebbackend.tag.domain.Tag;
import com.handong.cra.crawebbackend.tag.repository.TagRepository;
import com.handong.cra.crawebbackend.user.domain.User;
import com.handong.cra.crawebbackend.user.domain.UserRoleEnum;
import com.handong.cra.crawebbackend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final S3ImageService s3ImageService;
    private final UserRepository userRepository;
    private final TagRepository tagRepository;

    @Override
    @Transactional
    public CreateProjectDto createProject(final CreateProjectDto createProjectDto) {
        projectAuthCheck(createProjectDto.getUserId());
        if (createProjectDto.getSemester().length() > 4 || !createProjectDto.getSemester().contains("-")) {
            throw new ProjectSemesterParseException();
        }
        final Project project = Project.from(createProjectDto);
        project.setImageUrl(s3ImageService.transferImage(project.getImageUrl(), S3ImageCategory.PROJECT));

        if (createProjectDto.getTagIds() != null) {
            List<Tag> tags = tagRepository.findAllById(createProjectDto.getTagIds());
            project.getTags().addAll(tags);
        }

        final Project savedProject = projectRepository.save(project); // save 된 Entity 가져옴
        return CreateProjectDto.from(savedProject);
    }

    @Override
    @Transactional
    public UpdateProjectDto updateProject(final UpdateProjectDto updateProjectDto) {
        projectAuthCheck(updateProjectDto.getUserId());
        final Project project = projectRepository.findById(updateProjectDto.getId())
                .orElseThrow(ProjectNotFoundException::new);
        if (updateProjectDto.getTagIds() != null) {
            project.getTags().clear();
            List<Tag> tags = tagRepository.findAllById(updateProjectDto.getTagIds());
            project.getTags().addAll(tags);
        }

        String newImgUrl;
        // 수정됨
        if (updateProjectDto.getImageUrl().contains("temp/")) {
            s3ImageService.transferImage(project.getImageUrl(), S3ImageCategory.DELETED);
            newImgUrl = s3ImageService.transferImage(updateProjectDto.getImageUrl(), S3ImageCategory.PROJECT);
            updateProjectDto.setImageUrl(newImgUrl);
        }
        final Project updatedProject = project.update(updateProjectDto);
        return UpdateProjectDto.from(updatedProject);
    }

    @Override
    @Transactional
    public Boolean deleteProjectById(final UpdateProjectDto updateProjectDto) {
        projectAuthCheck(updateProjectDto.getUserId());
        final Project project = projectRepository.findById(updateProjectDto.getId())
                .orElseThrow(ProjectNotFoundException::new);
        s3ImageService.transferImage(project.getImageUrl(), S3ImageCategory.DELETED);
        project.delete();
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public DetailProjectDto getDetailProjectById(final Long projectId) {
        final Project project = projectRepository.findById(projectId).orElseThrow(ProjectNotFoundException::new);
        return DetailProjectDto.from(project);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ListProjectDto> getListProject() {
        final List<Project> projects = projectRepository.findAll();
        return projects.stream().map(ListProjectDto::from).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public PageProjectDto getPaginationProject(final PageProjectDataDto pageProjectDataDto) {
        HashMap<ProjectOrderBy, String> map = new HashMap<>();
        map.put(ProjectOrderBy.DATE, "createdAt");
        map.put(ProjectOrderBy.SEMESTER, "semester");
        Sort sort = Sort.by(map.get(pageProjectDataDto.getProjectOrderBy()));
        sort = (pageProjectDataDto.getIsASC()) ? sort.ascending() : sort.descending();
        final Pageable pageable = PageRequest.of(Math.toIntExact(pageProjectDataDto.getPage()), pageProjectDataDto.getPerPage(), sort);
        final Page<Project> projects = projectRepository.findAllByDeletedIsFalse(pageable);
        return PageProjectDto.of(projects.stream().map(ListProjectDto::from).toList(), projects.getTotalPages());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ListProjectDto> getProjectsByTagName(String tagName) {
        return projectRepository.findByTags_Name(tagName)
                .stream()
                .map(ListProjectDto::from)
                .toList();
    }

    private void projectAuthCheck(final Long userId) {
        final User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        if (!user.getRoles().hasRole(UserRoleEnum.ADMIN)) {
            throw new AuthForbiddenActionException();
        }
    }
}
