package com.handong.cra.crawebbackend.board.service;

import com.handong.cra.crawebbackend.board.domain.Board;
import com.handong.cra.crawebbackend.board.domain.Category;
import com.handong.cra.crawebbackend.board.dto.request.ReqCreateBoardDto;
import com.handong.cra.crawebbackend.board.dto.response.ResCreateBoardDto;
import com.handong.cra.crawebbackend.board.dto.response.ResDetailBoardDto;

import java.util.List;

public interface BoardService {
    public List<ResDetailBoardDto> getBoardsByCategory(Category category);
    public ResCreateBoardDto createBoard(ReqCreateBoardDto reqCreateBoardDto);
}
