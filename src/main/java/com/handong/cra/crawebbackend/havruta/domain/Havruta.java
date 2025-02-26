package com.handong.cra.crawebbackend.havruta.domain;

import com.handong.cra.crawebbackend.board.domain.Board;
import com.handong.cra.crawebbackend.common.domain.BaseEntity;
;
import com.handong.cra.crawebbackend.havruta.dto.CreateHavrutaDto;
import com.handong.cra.crawebbackend.havruta.dto.UpdateHavrutaDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Havruta extends BaseEntity {

    @NotNull
    @Column(length = 50, nullable = false)
    private String classname;

    @NotNull
    @Column(length = 50, nullable = false)
    private String professor;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "havruta")
    private List<Board> boards;

    public Havruta(String classname, String professor) {
        this.classname = classname;
        this.professor = professor;
    }

    public Havruta(CreateHavrutaDto createHavrutaDto) {
        this.classname = createHavrutaDto.getClassName();
        this.professor = createHavrutaDto.getProfessor();
    }

    public static Havruta from(CreateHavrutaDto createHavrutaDto) {
        return new Havruta(createHavrutaDto);
    }

    public Havruta update(UpdateHavrutaDto updateHavrutaDto) {
        this.classname = updateHavrutaDto.getClassName();
        this.professor = updateHavrutaDto.getProfessor();
        return this;
    }
}
