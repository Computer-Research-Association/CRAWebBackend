package com.handong.cra.crawebbackend.board.service;

import com.handong.cra.crawebbackend.board.domain.Board;
import com.handong.cra.crawebbackend.board.domain.Category;
import com.handong.cra.crawebbackend.board.dto.CreateBoardDto;
import com.handong.cra.crawebbackend.board.dto.DetailBoardDto;
import com.handong.cra.crawebbackend.board.dto.ListBoardDto;
import com.handong.cra.crawebbackend.board.dto.UpdateBoardDto;
import com.handong.cra.crawebbackend.board.dto.request.ReqCreateBoardDto;
import com.handong.cra.crawebbackend.board.dto.response.ResCreateBoardDto;
import com.handong.cra.crawebbackend.board.dto.response.ResDetailBoardDto;
import com.handong.cra.crawebbackend.board.dto.response.ResUpdateBoardDto;
import com.handong.cra.crawebbackend.board.repository.BoardRepository;
import com.handong.cra.crawebbackend.user.domain.User;
import com.handong.cra.crawebbackend.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public List<ListBoardDto> getBoardsByCategory(Category category) {
        // get data
        List<Board> boards = boardRepository.findAllByCategory(category);

        // parsing to dto
        List<ListBoardDto> dtos = boards.stream().map(ListBoardDto::new).toList();
        return dtos;
    }

    @Override
    @Transactional
    public CreateBoardDto createBoard(CreateBoardDto createBoardDto) {
        User user = userRepository.findById(createBoardDto.getUserId()).orElseThrow();
        Board board = new Board(user, createBoardDto);
        boardRepository.save(board);
//        new CreateBoardDto(board);
        return CreateBoardDto
                .builder()
                .userId(user.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .category(board.getCategory())
                .imageUrls(board.getImageUrls())
                .build();
    }

    @Override
    @Transactional
    public UpdateBoardDto updateBoard(UpdateBoardDto updateBoardDto) {
        Board board= boardRepository.findById(updateBoardDto.getId()).orElseThrow();
        board.update(updateBoardDto);
        return new UpdateBoardDto(board);
    }


    @Override
    @Transactional
    public Boolean deleteBoardById(Long id) {
        boardRepository.findById(id).orElseThrow().delete();
        return true;
    }

    @Override
    @Transactional
    public DetailBoardDto getDetailBoardById(Long id) {
        Board board = boardRepository.findById(id).orElseThrow();
        if (board.getDeleted()) return null;

        board.increaseView();
        return new DetailBoardDto(board);
    }
}
