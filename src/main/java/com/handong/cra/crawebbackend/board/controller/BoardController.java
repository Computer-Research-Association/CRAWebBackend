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
import com.handong.cra.crawebbackend.exception.ErrorResponse;
import com.handong.cra.crawebbackend.exception.board.PageSizeLimitExceededException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Board API", description = "Board 관련 컨트롤러.")
public class BoardController {
    private final BoardService boardService;

    @Value("${spring.data.page.MAX_PER_PAGE}")
    private Integer MAX_PAGE_SIZE;


    @Parameters(value = {
            @Parameter(name = "id", description = "Board id to delete"),
    })
    @Operation(summary = "Board 삭제", description = "유저의 권한 확인 이후 삭제 진행.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "삭제 완료"),
            @ApiResponse(responseCode = "403", description = "권한 없음"),
            @ApiResponse(responseCode = "404", description = "Board 정보 없음")
    })

    @DeleteMapping("/{boardId}")
    public ResponseEntity<Void> deleteBoard(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable Long boardId) {
        boardService.deleteBoardById(UpdateBoardDto.of(customUserDetails.getUserId(), boardId));
        return ResponseEntity.ok().build();
    }

    // 조회수 상승 없이 데이터 읽어옴
    @Parameters(value = {
            @Parameter(name = "id", description = "Board id to read detail"),
    })
    @Operation(summary = "Board 정보 가져오기", description = "아이디로 Board 의 정보를 가져옴 ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "정상 작동", content = @Content(schema = @Schema(implementation = ResDetailBoardDto.class))),
            @ApiResponse(responseCode = "403", description = "권한 없음", content = @Content()),
            @ApiResponse(responseCode = "404", description = "Board 정보 없음", content = @Content()),
    })
    @GetMapping("/view/{boardId}")
    public ResponseEntity<ResDetailBoardDto> getDetailBoard(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable Long boardId) {


        Long viewerId = null;
        if (customUserDetails != null) viewerId = customUserDetails.getUserId();
        ResDetailBoardDto resDetailBoardDto = ResDetailBoardDto.from(boardService.getDetailBoardById(boardId, viewerId));
        return ResponseEntity.ok().body(resDetailBoardDto);
    }

    // 조회수 상승
    @Parameters(value = {
            @Parameter(name = "id", description = "Board ascending View"),
    })
    @Operation(summary = "Board 조회수 증가", description = "아이디로 Board 의 조회수를 상승 ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "정상 작동"),
            @ApiResponse(responseCode = "403", description = "권한 없음"),
            @ApiResponse(responseCode = "404", description = "Board 정보 없음")
    })
    @PostMapping("/view/{boardId}")
    public ResponseEntity<Void> ascendingBoardView(@PathVariable Long boardId) {
        boardService.ascendingBoardView(boardId);
        return ResponseEntity.ok().build();
    }


    @Parameters(value = {
            @Parameter(name = "category", description = "0 = NOTICE, 1 = ACADEMIC, 2 = HAVRUTA"),
            @Parameter(name = "page", description = "Board Listing page"),
            @Parameter(name = "perPage", description = "각 페이지당 전달될 개수"),
            @Parameter(name = "orderBy", description = "정렬 방식 | 1. DATE 2.좋아요 수 순서"),
            @Parameter(name = "isASC", description = "오름차순"),
    })
    @Operation(summary = "Board 페이지단위로 가져오기", description = "페이지별 Board list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "정상 작동", content = @Content(schema = @Schema(implementation = ResListBoardDto.class))),
            @ApiResponse(responseCode = "403", description = "권한 없음", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Board 정보 없음", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{category}/page/{page}")
    public ResponseEntity<ResPageBoardDto> getPaginationBoard(
            @PathVariable Integer category,
            @PathVariable Long page, // 0부터 시작
            @RequestParam(required = false, defaultValue = "0") Integer perPage,
            @RequestParam(required = false, defaultValue = "0") Integer orderBy,
            @RequestParam(required = false, defaultValue = "true") Boolean isASC
    ) {
        if (perPage > MAX_PAGE_SIZE) {
            throw new PageSizeLimitExceededException();
        }

        PageBoardDataDto pageBoardDataDto = PageBoardDataDto.builder()
                .category(Category.values()[category])
                .page(page)
                .perPage(perPage)
                .orderBy(BoardOrderBy.values()[orderBy])
                .isASC(isASC)
                .build();

        return ResponseEntity.ok(ResPageBoardDto.from(boardService.getPaginationBoard(pageBoardDataDto)));
    }

    @GetMapping("/search")
    public ResponseEntity<ResSearchPageBoardDto> searchBoardByString(
            @RequestParam Long page, // 0부터 시작
            @RequestParam String keyword,
            @RequestParam(required = false) Integer category,
            @RequestParam(required = false, defaultValue = "10") Integer perPage,
            @RequestParam(required = false, defaultValue = "0") Integer orderBy,
            @RequestParam(required = false, defaultValue = "true") Boolean isASC
    ) {
        final PageBoardDataDto pageBoardDataDto = PageBoardDataDto.builder()
//                .category(Category.values()[category])
                .page(page)
                .perPage(perPage)
                .orderBy(BoardOrderBy.values()[orderBy])
                .isASC(isASC)
                .build();
        return ResponseEntity.ok(ResSearchPageBoardDto.from(boardService.searchPaginationBoardsByKeyword(pageBoardDataDto, keyword)));
    }


    @Operation(summary = "Board 생성")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "정상 작동", content = @Content(schema = @Schema(implementation = ResCreateBoardDto.class))),
            @ApiResponse(responseCode = "403", description = "권한 없음", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Board 정보 없음", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("")
    public ResponseEntity<ResCreateBoardDto> createBoard(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @Valid @RequestPart("board") ReqCreateBoardDto reqCreateBoardDto,
            @RequestPart(value = "file", required = false) MultipartFile file) {

        return ResponseEntity.status(HttpStatus.CREATED).body(ResCreateBoardDto.from(boardService
                .createBoard(CreateBoardDto.of(customUserDetails.getUserId(), reqCreateBoardDto, file))));
    }


    @Parameters(value = {
            @Parameter(name = "board id", description = "수정할 Board의 id"),
    })
    @Operation(summary = "Board 수정")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "정상 작동", content = @Content(schema = @Schema(implementation = ResUpdateBoardDto.class))),
            @ApiResponse(responseCode = "403", description = "권한 없음", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Board 정보 없음", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/{boardId}")
    public ResponseEntity<ResUpdateBoardDto> updateBoard(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable Long boardId,
            @RequestPart("board") ReqUpdateBoardDto reqUpdateBoardDto,
            @RequestPart(value = "file", required = false) MultipartFile file) {
        ResUpdateBoardDto resUpdateBoardDto;
        resUpdateBoardDto = ResUpdateBoardDto.from(boardService.updateBoard(UpdateBoardDto.of(customUserDetails.getUserId(), boardId, reqUpdateBoardDto, file)));
        return ResponseEntity.ok().body(resUpdateBoardDto);
    }


    @Parameters(value = {
            @Parameter(name = "id", description = "Board의 id"),
            @Parameter(name = "isLike", description = "좋아요 -> true 취소 -> false")
    })
    @Operation(summary = "Board 좋아요")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "정상 작동", content = @Content()),
            @ApiResponse(responseCode = "403", description = "권한 없음", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Board 정보 없음", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/like/{id}")
    public ResponseEntity<ResLikedBoardDto> BoardLike(
            @PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestParam(defaultValue = "true") Boolean isLike) {
        Integer likes = boardService.boardLike(id, customUserDetails.getUserId(), isLike);


        return ResponseEntity.ok(ResLikedBoardDto.of(isLike, likes));
    }
}
