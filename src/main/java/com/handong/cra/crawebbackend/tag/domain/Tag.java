package com.handong.cra.crawebbackend.tag.domain;

import com.handong.cra.crawebbackend.common.domain.BaseEntity;
import com.handong.cra.crawebbackend.board.domain.Board;
import com.handong.cra.crawebbackend.project.domain.Project;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tag extends BaseEntity {
    @FullTextField
    @Column(length = 50, nullable = false, unique = true)
    private String name;

    @ManyToMany(mappedBy = "tags", fetch = FetchType.LAZY)
    @BatchSize(size = 50)
    private List<Board> boards = new ArrayList<>();

    @ManyToMany(mappedBy = "tags", fetch = FetchType.LAZY)
    @BatchSize(size = 50)
    private List<Project> projects = new ArrayList<>();

    @Builder(access = AccessLevel.PRIVATE)
    public Tag(String name) {
        this.name = name;
    }

    public void updateName(String name) {
        this.name = name;
    }
    public static Tag of(String name) {
        return Tag.builder()
                .name(name)
                .build();
    }

}
