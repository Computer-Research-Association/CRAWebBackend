package com.handong.cra.crawebbackend.board.service;

import com.handong.cra.crawebbackend.board.dto.ListBoardDto;
import com.handong.cra.crawebbackend.board.dto.PageBoardDataDto;
import com.handong.cra.crawebbackend.board.dto.PageBoardDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface HavrutaBoardService {
    public List<ListBoardDto> getHavrutaBoards();

    public List<ListBoardDto> getHavrutaBoardsByHavrutaId(final Long havrutaId);

    public PageBoardDto getPaginationAllHavrutaBoard(final PageBoardDataDto pageBoardDataDto);

    public PageBoardDto getPaginationHavrutaBoard(final Long havrutaId, final PageBoardDataDto pageBoardDataDto);
}
