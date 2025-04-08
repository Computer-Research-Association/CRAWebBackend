package com.handong.cra.crawebbackend.board.controller;

import com.handong.cra.crawebbackend.auth.domain.CustomUserDetails;
import com.handong.cra.crawebbackend.board.domain.Category;
import com.handong.cra.crawebbackend.board.domain.BoardOrderBy;
import com.handong.cra.crawebbackend.board.dto.CreateBoardDto;
import com.handong.cra.crawebbackend.board.dto.PageBoardDataDto;
import com.handong.cra.crawebbackend.board.dto.UpdateBoardDto;
import com.handong.cra.crawebbackend.board.dto.request.ReqCreateBoardDto;
import com.handong.cra.crawebbackend.board.dto.request.ReqUpdateBoardDto;
import com.handong.cra.crawebbackend.board.dto.response.*;
import com.handong.cra.crawebbackend.board.service.BoardService;
import com.handong.cra.crawebbackend.exception.board.PageSizeLimitExceededException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
@Slf4j
public class BoardController {
    private final BoardService boardService;

    @Value("${spring.data.page.MAX_PER_PAGE}")
    private Integer MAX_PAGE_SIZE;

    @Operation(summary = "Board 삭제", description = "유저의 권한 확인 이후 삭제 진행.")
    @DeleteMapping("/{boardId}")
    public ResponseEntity<Void> deleteBoard(
            @AuthenticationPrincipal final CustomUserDetails customUserDetails,
            @PathVariable final Long boardId) {
        boardService.deleteBoardById(UpdateBoardDto
                .of(customUserDetails.getUserId(), boardId));
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Board 정보 조회", description = "아이디로 Board 의 정보 조회 ")
    @GetMapping("/view/{boardId}")
    public ResponseEntity<ResDetailBoardDto> getDetailBoard(
            @AuthenticationPrincipal final CustomUserDetails customUserDetails,
            @PathVariable final Long boardId) {
        Long viewerId = null; // TODO : refactor
        if (customUserDetails != null) {
            viewerId = customUserDetails.getUserId();
        }
        final ResDetailBoardDto resDetailBoardDto = ResDetailBoardDto
                .from(boardService.getDetailBoardById(boardId, viewerId));
        return ResponseEntity.ok().body(resDetailBoardDto);
    }

    @Operation(summary = "Board 조회수 증가", description = "아이디로 Board 조회수 상승 ")
    @PostMapping("/view/{boardId}")
    public ResponseEntity<Void> ascendingBoardView(@PathVariable final Long boardId) {
        boardService.ascendingBoardView(boardId);
        return ResponseEntity.ok().build();
    }

    @Parameters(value = {
            @Parameter(name = "category", description = "0 = NOTICE, 1 = ACADEMIC"),
            @Parameter(name = "page", description = "Board Listing page"),
            @Parameter(name = "perPage", description = "각 페이지당 전달될 개수"),
            @Parameter(name = "orderBy", description = "정렬 방식 | 1. DATE 2.좋아요 수 순서"),
            @Parameter(name = "isASC", description = "오름차순"),
    })
    @Operation(summary = "Board 페이지단위로 조회", description = "페이지별 Board list")
    @GetMapping("/{category}/page/{page}")
    public ResponseEntity<ResPageBoardDto> getPaginationBoard(
            @PathVariable final Integer category,
            @PathVariable final Long page, // 0부터 시작
            @RequestParam(required = false, defaultValue = "0") final Integer perPage,
            @RequestParam(required = false, defaultValue = "0") final Integer orderBy,
            @RequestParam(required = false, defaultValue = "true") final Boolean isASC
    ) {
        if (perPage > MAX_PAGE_SIZE) {
            throw new PageSizeLimitExceededException();
        }
        final PageBoardDataDto pageBoardDataDto = PageBoardDataDto.builder()
                .category(Category.values()[category])
                .page(page)
                .perPage(perPage)
                .orderBy(BoardOrderBy.values()[orderBy])
                .isASC(isASC)
                .build();
        return ResponseEntity.ok(ResPageBoardDto
                .from(boardService.getPaginationBoard(pageBoardDataDto)));
    }

    @GetMapping("/search")
    @Operation(summary = "Board 검색", description = "유저의 권한 확인 이후 검색")
    public ResponseEntity<ResSearchPageBoardDto> searchBoardByString(
            @RequestParam final Long page, // 0부터 시작
            @RequestParam final String keyword,
            @RequestParam(required = false) final Integer category,
            @RequestParam(required = false, defaultValue = "10") final Integer perPage,
            @RequestParam(required = false, defaultValue = "0") final Integer orderBy,
            @RequestParam(required = false, defaultValue = "true") final Boolean isASC
    ) {
        final PageBoardDataDto pageBoardDataDto = PageBoardDataDto.builder()
//                .category(Category.values()[category])
                .page(page)
                .perPage(perPage)
                .orderBy(BoardOrderBy.values()[orderBy])
                .isASC(isASC)
                .build();
        return ResponseEntity.ok(ResSearchPageBoardDto
                .from(boardService
                        .searchPaginationBoardsByKeyword(pageBoardDataDto, keyword)));
    }


    @Operation(summary = "Board 생성")
    @PostMapping("")
    public ResponseEntity<ResCreateBoardDto> createBoard(
            @AuthenticationPrincipal final CustomUserDetails customUserDetails,
            @Valid @RequestPart("board") final ReqCreateBoardDto reqCreateBoardDto,
            @RequestPart(value = "file", required = false) final MultipartFile file) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ResCreateBoardDto
                .from(boardService.createBoard(CreateBoardDto
                        .of(customUserDetails.getUserId(), reqCreateBoardDto, file))));
    }

    @Operation(summary = "Board 수정")
    @PutMapping("/{boardId}")
    public ResponseEntity<ResUpdateBoardDto> updateBoard(
            @AuthenticationPrincipal final CustomUserDetails customUserDetails,
            @PathVariable final Long boardId,
            @RequestPart("board") final ReqUpdateBoardDto reqUpdateBoardDto,
            @RequestPart(value = "file", required = false) final MultipartFile file) {
        final ResUpdateBoardDto resUpdateBoardDto = ResUpdateBoardDto
                .from(boardService.updateBoard(UpdateBoardDto
                        .of(customUserDetails.getUserId(), boardId, reqUpdateBoardDto, file)));
        return ResponseEntity.ok().body(resUpdateBoardDto);
    }

    @Operation(summary = "Board 좋아요")
    @PostMapping("/like/{boardId}")
    public ResponseEntity<ResLikedBoardDto> BoardLike(
            @PathVariable final Long boardId,
            @AuthenticationPrincipal final CustomUserDetails customUserDetails,
            @RequestParam(defaultValue = "true") Boolean isLike) {
        final Integer likes = boardService
                .boardLike(boardId, customUserDetails.getUserId(), isLike);
        return ResponseEntity.ok(ResLikedBoardDto
                .of(isLike, likes));
    }
}
