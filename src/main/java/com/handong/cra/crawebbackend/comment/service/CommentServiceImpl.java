package com.handong.cra.crawebbackend.comment.service;

import com.handong.cra.crawebbackend.board.domain.Board;
import com.handong.cra.crawebbackend.board.repository.BoardRepository;
import com.handong.cra.crawebbackend.comment.domain.Comment;
import com.handong.cra.crawebbackend.comment.dto.CreateCommentDto;
import com.handong.cra.crawebbackend.comment.dto.ListCommentDto;
import com.handong.cra.crawebbackend.comment.dto.UpdateCommentDto;
import com.handong.cra.crawebbackend.comment.repository.CommentRepository;
import com.handong.cra.crawebbackend.exception.auth.AuthForbiddenActionException;
import com.handong.cra.crawebbackend.exception.board.BoardNotFoundException;
import com.handong.cra.crawebbackend.exception.comment.CommentNestedReplyNotAllowedException;
import com.handong.cra.crawebbackend.exception.comment.CommentNotFoundException;
import com.handong.cra.crawebbackend.exception.user.UserNotFoundException;
import com.handong.cra.crawebbackend.user.domain.User;
import com.handong.cra.crawebbackend.user.domain.UserRoleEnum;
import com.handong.cra.crawebbackend.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public CreateCommentDto createComment(CreateCommentDto createCommentDto) {
        User user = userRepository.findById(createCommentDto.getUserId()).orElseThrow(UserNotFoundException::new);
        Board board = boardRepository.findById(createCommentDto.getBoardId()).orElseThrow(BoardNotFoundException::new);
        Comment comment;
        // 2차 대댓글은 달 수 없음
        if (createCommentDto.getParentCommentId() != null) {
            log.info("parentId is {}", createCommentDto.getParentCommentId());
            Comment parentComment = commentRepository.findById(createCommentDto.getParentCommentId()).orElseThrow(CommentNotFoundException::new);
            if (parentComment.getParentComment() != null) {
                throw new CommentNestedReplyNotAllowedException();
            }
            comment = new Comment(user, board, parentComment, createCommentDto);
        } else {
            log.info("parentId is null");
            comment = new Comment(user, board, createCommentDto);
        }

        comment = commentRepository.save(comment);
        return CreateCommentDto.from(comment);

    }

    @Override
    @Transactional
    public List<ListCommentDto> getCommentsByBoardId(Long boardId) {

        log.info("test here 1");
        Board board = boardRepository.findById(boardId).orElseThrow(BoardNotFoundException::new);

        log.info("test here 2");
        List<Comment> comments = commentRepository.findAllByBoardAndDeletedFalseAndParentCommentIsNull(board);
        log.info("test here 3");

        return comments.stream().map(ListCommentDto::from).toList();
    }

    @Override
    @Transactional
    public UpdateCommentDto updateComment(UpdateCommentDto updateCommentDto) {
        Comment comment = commentRepository.findWithUserById(updateCommentDto.getId()).orElseThrow(CommentNotFoundException::new);

        // 권한 검사
        commentAuthCheck(comment.getUser().getId(), updateCommentDto.getUserId());

        comment = comment.update(updateCommentDto);
        return UpdateCommentDto.from(comment);
    }

    @Override
    @Transactional
    public Boolean deleteCommentById(UpdateCommentDto updateCommentDto) {
        Comment comment = commentRepository.findWithUserById(updateCommentDto.getId()).orElseThrow(CommentNotFoundException::new);

        if (comment.getDeleted()) // 이미 삭제됨
            throw new CommentNotFoundException();

        // 권한 검사
        commentAuthCheck(comment.getUser().getId(), updateCommentDto.getUserId());

        comment.delete();
        // 상위 댓글인 경우
        if (comment.getParentComment() != null) {
            List<Comment> comments = comment.getCommentList();
            // 하위 댓글 삭제 처리
            for (Comment child : comments) child.delete();
        }

        return true;
    }

    @Override
    public Long getCommentCount(Long boardId) {
        boardRepository.findById(boardId).orElseThrow(BoardNotFoundException::new);
        return (long) commentRepository.findAllByBoardIdAndDeletedFalse(boardId).size();
    }

    private void commentAuthCheck(Long writerId, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        if (!writerId.equals(userId) && user.getRoles().hasRole(UserRoleEnum.ADMIN))
            throw new AuthForbiddenActionException();
    }
}
