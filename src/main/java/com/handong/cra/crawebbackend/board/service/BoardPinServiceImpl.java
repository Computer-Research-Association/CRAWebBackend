package com.handong.cra.crawebbackend.board.service;

import com.handong.cra.crawebbackend.board.domain.Board;
import com.handong.cra.crawebbackend.board.domain.BoardPin;
import com.handong.cra.crawebbackend.board.domain.Category;
import com.handong.cra.crawebbackend.board.dto.BoardPinDto;
import com.handong.cra.crawebbackend.board.repository.BoardPinRepository;
import com.handong.cra.crawebbackend.board.repository.BoardRepository;
import com.handong.cra.crawebbackend.exception.board.BoardNotFoundException;
import com.handong.cra.crawebbackend.exception.board.BoardPinDuplicateIdException;
import com.handong.cra.crawebbackend.exception.board.BoardPinNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardPinServiceImpl implements BoardPinService {

    private final BoardPinRepository boardPinRepository;
    private final BoardRepository boardRepository;

    @Override
    @Transactional
    public BoardPinDto setPin(final BoardPinDto boardPinDto) {
        final Board board = boardRepository.findBoardByIdAndDeletedFalse(boardPinDto.getBoardId())
                .orElseThrow(BoardNotFoundException::new);
        final BoardPin existingBoardPin = boardPinRepository.findBoardPinByBoardAndDeletedFalse(board);
        if (existingBoardPin != null) {
            existingBoardPin.setDeleted(false);
            return BoardPinDto.from(existingBoardPin);
        }
        return savePin(board);
    }

    private BoardPinDto savePin(final Board board) {
        final BoardPin boardPin = BoardPin.of(board);
        final BoardPin newBoardPin = boardPinRepository.save(boardPin);
        return BoardPinDto.from(newBoardPin);
    }


    @Override
    @Transactional
    public Boolean removePinById(final Long pinId) {
        final BoardPin boardPin = boardPinRepository.findBoardPinByIdAndDeletedFalse(pinId)
                .orElseThrow(BoardPinNotFoundException::new);
        boardPin.delete();
        return true;
    }

    @Override
    public List<BoardPinDto> getPinByBoardCategory(final Category category) {
        List<BoardPin> boardPins = boardPinRepository.findBoardPinByCategoryAndDeletedFalse(category);
        return boardPins.stream().map(BoardPinDto::from).toList();
    }

    @Override
    public List<BoardPinDto> getALlPins() {
        List<BoardPin> boardPins = boardPinRepository.findAllByDeletedFalse();
        return boardPins.stream().map(BoardPinDto::from).toList();
    }
}
