package com.handong.cra.crawebbackend.tag.repository;

import com.handong.cra.crawebbackend.tag.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {
    boolean existsByName(String name);
    Optional<Tag> findByName(String name);  // 이름으로 Tag 엔티티 조회
    List<Tag> findByNameIn(List<String> names);
}
