package com.handong.cra.crawebbackend.board.service;

import com.handong.cra.crawebbackend.board.domain.Board;
import com.handong.cra.crawebbackend.board.domain.Category;
import com.handong.cra.crawebbackend.board.dto.request.ReqCreateBoardDto;
import com.handong.cra.crawebbackend.board.dto.response.ResCreateBoardDto;
import com.handong.cra.crawebbackend.board.dto.response.ResDetailBoardDto;
import com.handong.cra.crawebbackend.board.repository.BoardRepository;
import com.handong.cra.crawebbackend.user.domain.User;
import com.handong.cra.crawebbackend.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public List<ResDetailBoardDto> getBoardsByCategory(Category category) {
        // get data
        List<Board> boards = new ArrayList<>();
        boards = boardRepository.findAllByCategory(category);

        // parsing to dto
        List<ResDetailBoardDto> dtos = boards.stream().map(ResDetailBoardDto::new).toList();

        // return
        return dtos;
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
