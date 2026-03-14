package com.handong.cra.crawebbackend.tag.repository;

import com.handong.cra.crawebbackend.tag.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {
    Optional<Tag> findByName(String name);
}