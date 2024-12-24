package com.handong.cra.crawebbackend.board.service;

import com.handong.cra.crawebbackend.board.domain.Board;
import com.handong.cra.crawebbackend.board.domain.Category;
import com.handong.cra.crawebbackend.board.dto.ReqCreateBoardDto;
import com.handong.cra.crawebbackend.board.dto.ResCreateBoardDto;
import com.handong.cra.crawebbackend.board.repository.BoardRepository;
import com.handong.cra.crawebbackend.user.domain.User;
import com.handong.cra.crawebbackend.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public List<Board> getBoardsByCategory(Category category) {
        List<Board> boards = new ArrayList<>();
        boards = boardRepository.findAllByCategory(category);
        return boards;
    }

    @Override
    @Transactional
    public ResCreateBoardDto createBoard(ReqCreateBoardDto reqCreateBoardDto) {
        User user = userRepository.findById(reqCreateBoardDto.getUserId()).orElseThrow();
        Board board = new Board(user, reqCreateBoardDto);
        boardRepository.save(board);

        return ResCreateBoardDto
                .builder()
                .id(board.getId())
                .userId(user.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .category(board.getCategory())
                .imageUrls(board.getImageUrls())
                .createdAt(board.getCreatedAt())
                .updatedAt(board.getUpdatedAt())
                .build();
    }
}
