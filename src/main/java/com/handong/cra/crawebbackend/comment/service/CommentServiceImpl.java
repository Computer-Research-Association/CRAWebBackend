package com.handong.cra.crawebbackend.comment.service;

import com.handong.cra.crawebbackend.board.domain.Board;
import com.handong.cra.crawebbackend.board.repository.BoardRepository;
import com.handong.cra.crawebbackend.comment.domain.Comment;
import com.handong.cra.crawebbackend.comment.dto.CreateCommentDto;
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
    public CreateCommentDto createComment(final CreateCommentDto createCommentDto) {
        final User user = userRepository.findById(createCommentDto.getUserId())
                .orElseThrow(UserNotFoundException::new);
        final Board board = boardRepository.findById(createCommentDto.getBoardId())
                .orElseThrow(BoardNotFoundException::new);
        Comment comment;
        // 2차 대댓글은 달 수 없음
        if (createCommentDto.getParentCommentId() != null) {
            final Comment parentComment = commentRepository.findById(createCommentDto.getParentCommentId())
                    .orElseThrow(CommentNotFoundException::new);
            if (parentComment.getParentComment() != null) {
                throw new CommentNestedReplyNotAllowedException();
            }
            comment = new Comment(user, board, parentComment, createCommentDto);
        } else {
            comment = new Comment(user, board, createCommentDto);
        }
        comment = commentRepository.save(comment);
        return CreateCommentDto.from(comment);
    }

    @Override
    @Transactional
    public UpdateCommentDto updateComment(final UpdateCommentDto updateCommentDto) {
        final Comment comment = commentRepository.findWithUserById(updateCommentDto.getId())
                .orElseThrow(CommentNotFoundException::new);
        commentAuthCheck(comment.getUser().getId(), updateCommentDto.getUserId());
        final Comment updatedComment = comment.update(updateCommentDto);
        return UpdateCommentDto.from(updatedComment);
    }

    @Override
    @Transactional
    public Boolean deleteCommentById(final UpdateCommentDto updateCommentDto) {
        final Comment comment = commentRepository.findWithUserById(updateCommentDto.getId())
                .orElseThrow(CommentNotFoundException::new);
        if (comment.getDeleted()) {// 이미 삭제됨
            throw new CommentNotFoundException();
        }
        // 권한 검사
        commentAuthCheck(comment.getUser().getId(), updateCommentDto.getUserId());
        comment.delete();
        // 상위 댓글인 경우
        if (comment.getParentComment() != null) {
            final List<Comment> comments = comment.getCommentList();
            // 하위 댓글 삭제 처리
            for (Comment child : comments) {
                child.delete();
            }
        }
        return true;
    }

    private void commentAuthCheck(final Long writerId, final Long userId) {
        final User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
        if (!writerId.equals(userId) && user.getRoles().hasRole(UserRoleEnum.ADMIN))
            throw new AuthForbiddenActionException();
    }
}
