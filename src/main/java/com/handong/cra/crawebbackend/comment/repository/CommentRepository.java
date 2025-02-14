package com.handong.cra.crawebbackend.comment.repository;

import com.handong.cra.crawebbackend.board.domain.Board;
import com.handong.cra.crawebbackend.comment.domain.Comment;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
//    public List<Comment> findAllByBoardAndDeletedFalse(Board board);

    // board 로 모든것 찾고, 삭제되지 않은 것들. and parentComment 가 null 이 아닌 값
    @EntityGraph(attributePaths = {"user", "parentComment", "commentList"})
    public List<Comment> findAllByBoardAndDeletedFalseAndParentCommentIsNull(Board board);

    @EntityGraph(attributePaths = {"user", "parentComment", "commentList"})
    public List<Comment> findAllByBoardIdAndDeletedFalse(Long boardId);

    @EntityGraph(attributePaths = {"user"})
    Optional<Comment> findWithUserById(Long id);

}
