package com.handong.cra.crawebbackend.project.repository;

import com.handong.cra.crawebbackend.project.domain.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface ProjectRepository extends JpaRepository<Project, Long> {
    Page<Project> findAllByDeletedIsFalse(Pageable pageable);
    List<Project> findByTags_Name(String tagName);
}
