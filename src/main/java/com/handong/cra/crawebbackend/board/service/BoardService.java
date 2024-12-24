package com.handong.cra.crawebbackend.board.service;

import com.handong.cra.crawebbackend.board.domain.Board;
import com.handong.cra.crawebbackend.board.domain.Category;
import com.handong.cra.crawebbackend.board.dto.ReqCreateBoardDto;
import com.handong.cra.crawebbackend.board.dto.ResCreateBoardDto;

import java.util.List;

public interface BoardService {
    public List<Board> getBoardsByCategory(Category category);
    public ResCreateBoardDto createBoard(ReqCreateBoardDto reqCreateBoardDto);
}
