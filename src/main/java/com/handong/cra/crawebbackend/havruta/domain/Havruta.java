package com.handong.cra.crawebbackend.havruta.domain;

import com.handong.cra.crawebbackend.board.domain.Board;
import com.handong.cra.crawebbackend.common.domain.BaseEntity;
;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Havruta extends BaseEntity {

    @Column(length = 50, nullable = false)
    private String className;

    @Column(length = 50, nullable = false)
    private String professor;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "parent")
    private List<Board> boards;
}
