package com.handong.cra.crawebbackend.board.service;

import com.handong.cra.crawebbackend.board.domain.Board;
import com.handong.cra.crawebbackend.board.domain.Category;
import com.handong.cra.crawebbackend.board.dto.BoardPinDto;
import com.handong.cra.crawebbackend.board.dto.ListBoardDto;
import com.handong.cra.crawebbackend.board.dto.PageBoardDataDto;
import com.handong.cra.crawebbackend.board.dto.PageBoardDto;
import com.handong.cra.crawebbackend.board.repository.BoardRepository;
import com.handong.cra.crawebbackend.exception.havruta.HavrutaNotFoundException;
import com.handong.cra.crawebbackend.havruta.domain.Havruta;
import com.handong.cra.crawebbackend.havruta.repository.HavrutaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class HavrutaBoardServiceImpl implements HavrutaBoardService {

    private final HavrutaRepository havrutaRepository;
    private final BoardRepository boardRepository;
    private final BoardService boardService;
    private final BoardPinService boardPinService;


    @Override
    public List<ListBoardDto> getHavrutaBoards() {
        final List<Board> boards = boardRepository.findByCategory(Category.HAVRUTA);
        return boards.stream().map(ListBoardDto::from).filter(Objects::nonNull).toList();
    }

    @Override
    public List<ListBoardDto> getHavrutaBoardsByHavrutaId(Long havrutaId) {
        Havruta havruta = havrutaRepository.findById(havrutaId).orElseThrow(HavrutaNotFoundException::new);
        List<Board> havrutas = havruta.getBoards();
        return havrutas.stream().map(ListBoardDto::from).filter(Objects::nonNull).toList();
    }

    @Override
    public PageBoardDto getPaginationAllHavrutaBoard(final PageBoardDataDto pageBoardDataDto) {
        final Pageable pageable = boardService.getPageable(pageBoardDataDto);
        final Page<Board> boards = boardRepository.findByCategoryAndDeletedFalse(Category.HAVRUTA, pageable);
        final List<BoardPinDto> boardPinDtos = boardPinService.getPinByBoardCategory(pageBoardDataDto.getCategory());
        return PageBoardDto.builder()
                .listBoardDtos(boards.stream().map(ListBoardDto::from).toList())
                .boardPinDtos(boardPinDtos)
                .totalPages(boards.getTotalPages())
                .build();
    }

    @Override
    public PageBoardDto getPaginationHavrutaBoard(final Long havrutaId, final PageBoardDataDto pageBoardDataDto) {
        final Pageable pageable = boardService.getPageable(pageBoardDataDto);
        final Havruta havruta = havrutaRepository.findById(havrutaId).orElseThrow();
        final Page<Board> boards = boardRepository.findAllByHavrutaAndDeletedFalse(havruta, pageable);
        final List<BoardPinDto> boardPinDtos = boardPinService.getPinByBoardCategory(pageBoardDataDto.getCategory());
        return PageBoardDto.builder()
                .listBoardDtos(boards.stream().map(ListBoardDto::from).toList())
                .boardPinDtos(boardPinDtos)
                .totalPages(boards.getTotalPages())
                .build();
    }

}
