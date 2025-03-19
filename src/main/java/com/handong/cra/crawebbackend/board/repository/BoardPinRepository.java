package com.handong.cra.crawebbackend.board.repository;

import com.handong.cra.crawebbackend.board.domain.Board;
import com.handong.cra.crawebbackend.board.domain.BoardPin;
import com.handong.cra.crawebbackend.board.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardPinRepository extends JpaRepository<BoardPin, Long> {
    public Optional<BoardPin> findBoardPinByIdAndDeletedFalse(Long id);

    public List<BoardPin> findAllByDeletedFalse();

    public List<BoardPin> findBoardPinByCategoryAndDeletedFalse(Category category);

    public BoardPin findBoardPinByBoardIdAndDeletedFalse(Long boardId);
    public BoardPin findBoardPinByBoardAndDeletedFalse(Board board);
}
