package com.handong.cra.crawebbackend.board.service;

import com.handong.cra.crawebbackend.board.domain.Category;
import com.handong.cra.crawebbackend.board.dto.BoardPinDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BoardPinService {

    /**
     * 고정 공지 설정
     *
     * @param boardPinDto 고정 공지 설정 데이터 DTO
     */
    BoardPinDto setPin(final BoardPinDto boardPinDto);

    /**
     * 고정 공지 삭제
     *
     * @param pinId 고정 공지 삭제 데이터 DTO
     */
    Boolean removePinById(final Long pinId);

    /**
     * 카테고리별 고정 공지 조회
     *
     * @param category 카테고리
     */
    List<BoardPinDto> getPinByBoardCategory(Category category);

    /**
     * 모든 고정 공지 확인
     */
    List<BoardPinDto> getALlPins();
}
