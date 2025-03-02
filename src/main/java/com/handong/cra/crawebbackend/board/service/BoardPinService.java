package com.handong.cra.crawebbackend.board.service;

import com.handong.cra.crawebbackend.board.domain.Category;
import com.handong.cra.crawebbackend.board.dto.BoardPinDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BoardPinService {
    public BoardPinDto setPin(final BoardPinDto boardPinDto);

    public Boolean removePinById(final Long pinId);

    public List<BoardPinDto> getPinByBoardCategory(Category category);

    public List<BoardPinDto> getALlPins();
}
