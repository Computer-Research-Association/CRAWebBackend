package com.handong.cra.crawebbackend.havruta.repository;

import com.handong.cra.crawebbackend.board.domain.Board;
import com.handong.cra.crawebbackend.havruta.domain.Havruta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HavrutaRepository extends JpaRepository<Havruta, Long> {
    public Havruta findHavrutaByIdAndDeletedFalse(Long id);
}
