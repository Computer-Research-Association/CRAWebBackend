package com.handong.cra.crawebbackend.tag.domain;

import com.handong.cra.crawebbackend.common.domain.BaseEntity;
import com.handong.cra.crawebbackend.board.domain.Board;
import com.handong.cra.crawebbackend.project.domain.Project;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tag extends BaseEntity {
    @FullTextField
    @Column(length = 50, nullable = false, unique = true)
    private String name;

    @ManyToMany(mappedBy = "tags")
    private List<Board> boards = new ArrayList<>();

    @ManyToMany(mappedBy = "tags")
    private List<Project> projects = new ArrayList<>();

//    빌더로 ㄱㄱ private
    public Tag(String name) {
        this.name = name;
    }

//    createdDto 하나 만들자
    public static Tag of(String name) {
        return new Tag(name);
    }
}