package com.handong.cra.crawebbackend.board.repository;

import com.handong.cra.crawebbackend.board.domain.Board;
import com.handong.cra.crawebbackend.board.domain.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
//    public List<Board> findAllByCategory(Category category);
    public List<Board> findAllByCategoryAndDeletedFalse(Category category);
    public Page<Board> findAllByCategoryAndDeletedFalse(Category category,Pageable pageable);

    public Board findBoardByIdAndDeletedFalse(Long id);

}
