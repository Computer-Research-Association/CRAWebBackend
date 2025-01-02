package com.handong.cra.crawebbackend.board.service;

import com.handong.cra.crawebbackend.board.domain.Board;
import com.handong.cra.crawebbackend.board.domain.Category;
import com.handong.cra.crawebbackend.board.domain.OrderBy;
import com.handong.cra.crawebbackend.board.dto.*;
import com.handong.cra.crawebbackend.board.repository.BoardRepository;
import com.handong.cra.crawebbackend.user.domain.User;
import com.handong.cra.crawebbackend.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.PageRequest;

import org.springframework.data.domain.Pageable;
import org.springdoc.core.converters.PageableOpenAPIConverter;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;


@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final PageableOpenAPIConverter pageableOpenAPIConverter;

    @Override
    @Transactional
    public List<ListBoardDto> getBoardsByCategory(Category category) {
        // get data
        List<Board> boards = boardRepository.findAllByCategory(category);

        // parsing to dto
        List<ListBoardDto> dtos = boards.stream().map(ListBoardDto::from).filter(Objects::nonNull).toList();
        return dtos;
    }

    @Override
    public List<ListBoardDto> getPaginationBoard(Long page, Integer perPage, OrderBy orderBy, Boolean isASC) {

        HashMap<OrderBy, String> map = new HashMap<>();
        map.put(OrderBy.DATE, "createdAt");
        map.put(OrderBy.LIKECOUNT, "likeCount");

        Sort sort = Sort.by(map.get(orderBy));

        sort = (isASC) ? sort.ascending() : sort.descending();

        Pageable pageable = PageRequest.of(Math.toIntExact(page), perPage, sort);
        List<Board> boards = boardRepository.findAllByDeletedIsFalse(pageable);

        return boards.stream().map(ListBoardDto::from).toList();
    }

    @Override
    @Transactional
    public CreateBoardDto createBoard(CreateBoardDto createBoardDto) {
        User user = userRepository.findById(createBoardDto.getUserId()).orElseThrow();
        Board board = Board.of(user, createBoardDto);
        boardRepository.save(board);
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
        Board board = boardRepository.findById(updateBoardDto.getId()).orElseThrow();
        board.update(updateBoardDto);
        return UpdateBoardDto.from(board);
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
        return DetailBoardDto.from(board);
    }
}
