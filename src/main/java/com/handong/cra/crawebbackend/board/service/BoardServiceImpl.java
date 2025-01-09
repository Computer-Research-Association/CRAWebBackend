package com.handong.cra.crawebbackend.board.service;

import com.handong.cra.crawebbackend.board.domain.Board;
import com.handong.cra.crawebbackend.board.domain.Category;
import com.handong.cra.crawebbackend.board.domain.BoardOrderBy;
import com.handong.cra.crawebbackend.board.dto.*;
import com.handong.cra.crawebbackend.board.repository.BoardRepository;
import com.handong.cra.crawebbackend.user.domain.User;
import com.handong.cra.crawebbackend.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.PageRequest;

import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;


@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public List<ListBoardDto> getBoardsByCategory(Category category) {
        // get data
        List<Board> boards = boardRepository.findAllByCategoryAndDeletedFalse(category);

        // parsing to dto
        return boards.stream().map(ListBoardDto::from).filter(Objects::nonNull).toList();
    }

    @Override
    public List<ListBoardDto> getPaginationBoard(Long page, Integer perPage, BoardOrderBy boardOrderBy, Boolean isASC) {

        HashMap<BoardOrderBy, String> map = new HashMap<>();
        map.put(BoardOrderBy.DATE, "createdAt");
        map.put(BoardOrderBy.LIKECOUNT, "likeCount");

        Sort sort = Sort.by(map.get(boardOrderBy));

        sort = (isASC) ? sort.ascending() : sort.descending();

        Pageable pageable = PageRequest.of(Math.toIntExact(page), perPage, sort);
        Page<Board> boards = boardRepository.findAllByDeletedIsFalse(pageable);

        return boards.stream().map(ListBoardDto::from).toList();
    }

    @Override
    @Transactional
    public CreateBoardDto createBoard(CreateBoardDto createBoardDto) {
        User user = userRepository.findById(createBoardDto.getUserId()).orElseThrow(()-> new RuntimeException("no user"));
        Board board = Board.of(user, createBoardDto);
        board = boardRepository.save(board);
        return CreateBoardDto.from(board);
    }

    @Override
    @Transactional
    public UpdateBoardDto updateBoard(UpdateBoardDto updateBoardDto) {
        Board board = boardRepository.findById(updateBoardDto.getId()).orElseThrow(()-> new RuntimeException("no data"));
        board = board.update(updateBoardDto);
        return UpdateBoardDto.from(board);
    }

    @Override
    @Transactional
    public Boolean deleteBoardById(Long id) {
        boardRepository.findById(id).orElseThrow(() -> new RuntimeException("no data")).delete();
        return true;
    }

    @Override
    public DetailBoardDto getDetailBoardById(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(()-> new RuntimeException("no data"));
        return DetailBoardDto.from(board);
    }

    @Override
    @Transactional
    public void ascendingBoardView(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new RuntimeException("no data"));
        board.increaseView();
    }
}
