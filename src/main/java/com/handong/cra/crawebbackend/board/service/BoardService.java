package com.handong.cra.crawebbackend.board.service;

import com.handong.cra.crawebbackend.board.domain.Category;
import com.handong.cra.crawebbackend.board.dto.*;

import java.util.List;

public interface BoardService {
    public List<ListBoardDto> getBoardsByCategory(Category category);
    public PageBoardDto getPaginationBoard(PageBoardDataDto pageBoardDataDto);

    public CreateBoardDto createBoard(CreateBoardDto createBoardDto);
    public UpdateBoardDto updateBoard(UpdateBoardDto updateBoardDto);
    public Boolean deleteBoardById(UpdateBoardDto updateBoardDto);
    public DetailBoardDto getDetailBoardById(Long id, Long userId);
    public void ascendingBoardView(Long id);
    public Integer boardLike(Long boardId, Long userId, Boolean isLiked);
//    public Pageable getPageable (PageBoardDto pageBoardDto);

    public List<ListBoardDto> getHavrutaBoards();
    public List<ListBoardDto> getHavrutaBoardsByHavrutaId(Long havrutaId);
    public PageBoardDto getPaginationAllHavrutaBoard(PageBoardDataDto pageBoardDataDto);
    public PageBoardDto getPaginationHavrutaBoard(Long havrutaId, PageBoardDataDto pageBoardDataDto);
}
