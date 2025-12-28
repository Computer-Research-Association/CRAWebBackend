package com.handong.cra.crawebbackend.board.service;

import com.handong.cra.crawebbackend.board.domain.Category;
import com.handong.cra.crawebbackend.board.dto.*;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BoardService {
    /**
     * 카테고리별 전체 게시글 조회
     *
     * @param category 카테고리
     */
    List<ListBoardDto> getBoardsByCategory(Category category);

    /**
     * 페이지별 게시글 조회
     *
     * @param pageBoardDataDto 데이터 페이징 조회 데이터 DTO
     */
    PageBoardDto getPaginationBoard(PageBoardDataDto pageBoardDataDto);

    /**
     * 게시글 생성
     *
     * @param createBoardDto 게시글 생성 데이터 DTO
     */
    CreateBoardDto createBoard(CreateBoardDto createBoardDto);

    /**
     * 게시글 수정
     *
     * @param updateBoardDto 게시글 수정 데이터 DTO
     */
    UpdateBoardDto updateBoard(UpdateBoardDto updateBoardDto);

    /**
     * 게시글 삭제
     *
     * @param updateBoardDto 게시글 삭제 데이터 DTO
     */
    Boolean deleteBoardById(UpdateBoardDto updateBoardDto);

    /**
     * 게시글 조회
     *
     * @param boardId 조회할 게시글 ID
     * @param userId  로그인한 유저의 ID (비로그인시 null)
     */
    DetailBoardDto getDetailBoardById(Long boardId, Long userId);

    /**
     * 게시글 조회수 증가
     *
     * @param boardId 증가할 게시글 ID
     */
    void ascendingBoardView(Long boardId);

    /**
     * 게시글 좋아요
     *
     * @param boardId 좋아요 처리할 게시글 ID
     * @param userId  유저의 ID
     * @param isLiked 좋아요 조건 (true -> 좋아요, false -> 취소)
     */
    Integer boardLike(Long boardId, Long userId, Boolean isLiked);

    /**
     * 페이징처리 데이터 생성
     * <br />
     * 페이지 처리에 필요한 데이터 생성
     *
     * @param pageBoardDataDto 체이징 처리 데이터 DTO
     */
    Pageable getPageable(PageBoardDataDto pageBoardDataDto);

    /**
     * 키워드로 게시글 검색
     *
     * @param pageBoardDataDto 페이징 처리 데이터 DTO
     * @param keyword          검색어
     */
    SearchPageBoardDto searchPaginationBoardsByKeyword(PageBoardDataDto pageBoardDataDto, String keyword);

    /**
     * 검색 알고리즘 데이터 인덱싱
     * <br />서버 실행시 실행, 주기적으로 실행 필요(스케줄링 처리 완)
     */
    void setSearchIndex();
}
