package com.handong.cra.crawebbackend.comment.service;

import com.handong.cra.crawebbackend.board.domain.Board;
import com.handong.cra.crawebbackend.board.repository.BoardRepository;
import com.handong.cra.crawebbackend.comment.domain.Comment;
import com.handong.cra.crawebbackend.comment.dto.CreateCommentDto;
import com.handong.cra.crawebbackend.comment.dto.ListCommentDto;
import com.handong.cra.crawebbackend.comment.dto.UpdateCommentDto;
import com.handong.cra.crawebbackend.comment.repository.CommentRepository;
import com.handong.cra.crawebbackend.user.domain.User;
import com.handong.cra.crawebbackend.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public CreateCommentDto createComment(CreateCommentDto createCommentDto) {
        User user = userRepository.findById(createCommentDto.getUserId()).orElseThrow();
        Board board = boardRepository.findById(createCommentDto.getBoardId()).orElseThrow();

        Comment comment = new Comment(user, board, createCommentDto);
        commentRepository.save(comment);
        return CreateCommentDto
                .builder()
                .userId(user.getId())
                .boardId(board.getId())
                .content(comment.getContent())
                .build();
    }

    @Override
    @Transactional
    public List<ListCommentDto> getCommentsByBoardId(Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow();
        List<Comment> comments = commentRepository.findAllByBoardAndDeletedFalse(board);

        List<ListCommentDto> dtos = comments.stream().map(ListCommentDto::new).toList();
        return dtos;
    }

    @Override
    @Transactional
    public UpdateCommentDto updateComment(UpdateCommentDto updateCommentDto) {
        Comment comment = commentRepository.findById(updateCommentDto.getId()).orElseThrow();
        comment.update(updateCommentDto);
        return new UpdateCommentDto(comment);
    }

    @Override
    @Transactional
    public Boolean deleteCommentById(Long id) {
        commentRepository.findById(id).orElseThrow().delete();
        return true;
    }
}
