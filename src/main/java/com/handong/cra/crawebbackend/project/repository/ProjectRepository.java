package com.handong.cra.crawebbackend.project.repository;

import com.handong.cra.crawebbackend.project.domain.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;


public interface ProjectRepository extends JpaRepository<Project, Long> {
    public Page<Project> findAllByDeletedIsFalse(Pageable pageable);
}
