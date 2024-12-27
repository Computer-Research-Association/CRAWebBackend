package com.handong.cra.crawebbackend.havruta.domain;

import com.handong.cra.crawebbackend.board.domain.Board;
import com.handong.cra.crawebbackend.common.domain.BaseEntity;
import com.handong.cra.crawebbackend.havruta.dto.UpdateHavrutaDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Havruta extends BaseEntity{
    @Column(name = "class_name")
    private String className;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<Board> boards;

    public void update(UpdateHavrutaDto updateHavrutaDto) {
        this.
    }
}
