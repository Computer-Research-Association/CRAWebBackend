package com.handong.cra.crawebbackend.board.service;

import com.handong.cra.crawebbackend.board.domain.Category;
import com.handong.cra.crawebbackend.board.dto.*;
import org.springframework.data.domain.Pageable;

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

    public Pageable getPageable(PageBoardDataDto pageBoardDataDto);

    public SearchPageBoardDto searchPaginationBoardsByKeyword(PageBoardDataDto pageBoardDataDto, String keyword);

    public void setSearchIndex();
}
