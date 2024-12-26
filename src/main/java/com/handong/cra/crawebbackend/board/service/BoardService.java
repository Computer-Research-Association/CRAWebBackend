package com.handong.cra.crawebbackend.board.service;

import com.handong.cra.crawebbackend.board.domain.Board;
import com.handong.cra.crawebbackend.board.domain.Category;
import com.handong.cra.crawebbackend.board.dto.CreateBoardDto;
import com.handong.cra.crawebbackend.board.dto.ListBoardDto;
import com.handong.cra.crawebbackend.board.dto.UpdateBoardDto;
import com.handong.cra.crawebbackend.board.dto.request.ReqCreateBoardDto;
import com.handong.cra.crawebbackend.board.dto.response.ResCreateBoardDto;
import com.handong.cra.crawebbackend.board.dto.response.ResDetailBoardDto;
import com.handong.cra.crawebbackend.board.dto.response.ResListBoardDto;
import com.handong.cra.crawebbackend.board.dto.response.ResUpdateBoardDto;
import org.hibernate.sql.Update;

import java.util.List;

public interface BoardService {
    public List<ListBoardDto> getBoardsByCategory(Category category);
    public CreateBoardDto createBoard(CreateBoardDto createBoardDto);
    public UpdateBoardDto updateBoard(UpdateBoardDto updateBoardDto);
    public Boolean deleteBoardById(Long id);
}
