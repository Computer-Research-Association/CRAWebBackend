package com.handong.cra.crawebbackend.project.repository;

import com.handong.cra.crawebbackend.project.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {

}
