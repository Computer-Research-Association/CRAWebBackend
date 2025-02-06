package com.handong.cra.crawebbackend.board.service;

import com.handong.cra.crawebbackend.board.domain.Category;
import com.handong.cra.crawebbackend.board.domain.BoardOrderBy;
import com.handong.cra.crawebbackend.board.dto.*;

import java.util.List;

public interface BoardService {
    public List<ListBoardDto> getBoardsByCategory(Category category);
    public List<ListBoardDto> getPaginationBoard(Category category, Long page, Integer perPage, BoardOrderBy orderBy, Boolean isASC);

    public CreateBoardDto createBoard(CreateBoardDto createBoardDto);
    public UpdateBoardDto updateBoard(UpdateBoardDto updateBoardDto);
    public Boolean deleteBoardById(Long userId, Long boardId);
    public DetailBoardDto getDetailBoardById(Long id);
    public void ascendingBoardView(Long id);
    public void boardLike(Long boardId, Long userId, Boolean isLiked);
}
