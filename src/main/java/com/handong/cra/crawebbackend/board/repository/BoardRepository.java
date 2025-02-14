package com.handong.cra.crawebbackend.board.repository;

import com.handong.cra.crawebbackend.board.domain.Board;
import com.handong.cra.crawebbackend.board.domain.Category;
import com.handong.cra.crawebbackend.havruta.domain.Havruta;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {
    @EntityGraph(attributePaths = {"likedUsers"})
    public Optional<Board> findById(Long boardId);

//    public List<Board> findAllByCategory(Category category);
    public List<Board> findAllByCategoryAndDeletedFalse(Category category);

    public Page<Board> findAllByCategoryAndDeletedFalse(Category category,Pageable pageable);
    //public Page<Board> findByDeletedFalse(Pageable pageable);

    public Page<Board>  findAllByHavrutaAndDeletedFalse(Havruta havruta, Pageable pageable);

    @EntityGraph(attributePaths = {"likedUsers"})
    public Board findBoardByIdAndDeletedFalse(Long id);

    public List<Board> findByCategory(Category category);


    //test
    public Page<Board> findByCategoryAndDeletedFalse(Category category, Pageable pageable);
}
