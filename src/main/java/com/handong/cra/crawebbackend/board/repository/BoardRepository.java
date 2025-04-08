package com.handong.cra.crawebbackend.board.repository;

import com.handong.cra.crawebbackend.board.domain.Board;
import com.handong.cra.crawebbackend.board.domain.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {
    @Query("SELECT b FROM Board b LEFT JOIN FETCH b.likedUsers WHERE b.id = :boardId")
    Optional<Board> findByIdWithLikedUsers(@Param("boardId") Long boardId);

    List<Board> findAllByCategoryAndDeletedFalse(Category category);

    Page<Board> findAllByCategoryAndDeletedFalse(Category category, Pageable pageable);

    @EntityGraph(attributePaths = {"likedUsers"})
    Optional<Board> findBoardByIdAndDeletedFalse(Long id);

    List<Board> findByCategory(Category category);

    Page<Board> findByCategoryAndDeletedFalse(Category category, Pageable pageable);

    Page<Board> findByTitleContainingOrContentContainingAndDeletedFalse(String titleKeyword, String contentKeyword, Pageable pageable);
}
