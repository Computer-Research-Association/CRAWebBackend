package com.handong.cra.crawebbackend.board.controller;

import com.handong.cra.crawebbackend.board.domain.Category;
import com.handong.cra.crawebbackend.board.domain.BoardOrderBy;
import com.handong.cra.crawebbackend.board.dto.CreateBoardDto;
import com.handong.cra.crawebbackend.board.dto.ListBoardDto;
import com.handong.cra.crawebbackend.board.dto.UpdateBoardDto;
import com.handong.cra.crawebbackend.board.dto.request.ReqCreateBoardDto;
import com.handong.cra.crawebbackend.board.dto.request.ReqUpdateBoardDto;
import com.handong.cra.crawebbackend.board.dto.response.ResCreateBoardDto;
import com.handong.cra.crawebbackend.board.dto.response.ResDetailBoardDto;
import com.handong.cra.crawebbackend.board.dto.response.ResListBoardDto;
import com.handong.cra.crawebbackend.board.dto.response.ResUpdateBoardDto;
import com.handong.cra.crawebbackend.board.service.BoardService;
import com.handong.cra.crawebbackend.exception.ErrorResponse;
import com.handong.cra.crawebbackend.exception.board.BoardIllegalCategoryException;
import com.handong.cra.crawebbackend.exception.board.BoardPageSizeLimitExceededException;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
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
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBoard(@PathVariable Long id) {
        boardService.deleteBoardById(id);
        return ResponseEntity.ok().build();
    }



    @Parameters(value = {
            @Parameter(name = "category", description = "0 = NOTICE, 1 = ACADEMIC, 2 = HAVRUTA"),
    })
    @Operation(summary = "Category 로 전체 Board List 가져오기", description = "카테고리로 데이터의 리스트를 읽어옴.많은 데이터를 가져올 수 있기 때문에, 권장되지 않음. (page 사용 권장) [추후 admin만 접근 가능하게 할 예정]")
    @ApiResponses(value = {

            @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = ResListBoardDto.class))),
            @ApiResponse(responseCode = "403", description = "권한 없음", content = @Content()),
            @ApiResponse(responseCode = "404", description = "category 정보 없음", content = @Content())
    })
    //TODO 어드민만 접근 가능하게 할것
    @GetMapping("/{category}")
    public ResponseEntity<List<ResListBoardDto>> getBoardsByCategory(@PathVariable Integer category) {
        if (category < 0 || category >= Category.values().length) {
            throw new BoardIllegalCategoryException();
        }

        return ResponseEntity.ok().body(boardService.getBoardsByCategory(Category.values()[category])
                .stream().map(ResListBoardDto::from).toList());
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
    @GetMapping("/view/{id}")
    public ResponseEntity<ResDetailBoardDto> getDetailBoard(@PathVariable Long id) {
        ResDetailBoardDto resDetailBoardDto = ResDetailBoardDto.from(boardService.getDetailBoardById(id));
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
    @PostMapping("/view/{id}")
    public ResponseEntity<Void> ascendingBoardView(@PathVariable Long id) {
        boardService.ascendingBoardView(id);
        return ResponseEntity.ok().build();
    }


    // TODO Category 처리 할것
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
    public ResponseEntity<List<ResListBoardDto>> getPaginationBoard(
            @PathVariable Integer category,
            @PathVariable Long page,
            @RequestParam(required = false, defaultValue = "0") Integer perPage,
            @RequestParam(required = false, defaultValue = "0") Integer orderBy,
            @RequestParam(required = false, defaultValue = "true") Boolean isASC
    ) {
        if (perPage > MAX_PAGE_SIZE) {
            throw new BoardPageSizeLimitExceededException();
        }
        List<ListBoardDto> listBoardDtos = boardService.getPaginationBoard(Category.values()[category],page, perPage, BoardOrderBy.values()[orderBy], isASC);
        return ResponseEntity.ok(listBoardDtos.stream().map(ResListBoardDto::from).toList());
    }

    @Operation(summary = "Board 생성")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "정상 작동", content = @Content(schema = @Schema(implementation = ResCreateBoardDto.class))),
            @ApiResponse(responseCode = "403", description = "권한 없음", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Board 정보 없음", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("")
    public ResponseEntity<ResCreateBoardDto> createBoard(@Valid @RequestPart("board") ReqCreateBoardDto reqCreateBoardDto,
                                                         @RequestPart("files") List<MultipartFile> files) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ResCreateBoardDto.from(boardService
                .createBoard(CreateBoardDto.of(reqCreateBoardDto, reqCreateBoardDto.getUserId(), files))));
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
    @PutMapping("/{id}")
    public ResponseEntity<ResUpdateBoardDto> updateBoard(@PathVariable Long id,
                                                         @RequestPart("board") ReqUpdateBoardDto reqUpdateBoardDto,
                                                         @RequestPart(value = "files") List<MultipartFile> files) {
        ResUpdateBoardDto resUpdateBoardDto;
        resUpdateBoardDto = ResUpdateBoardDto.from(boardService.updateBoard(UpdateBoardDto.of(id, reqUpdateBoardDto, files)));
        return ResponseEntity.ok().body(resUpdateBoardDto);
    }


    @Parameters(value = {
            @Parameter(name = "id", description = "Board의 id"),
            @Parameter(name = "userId", description = "User Id (하드코딩)"),
            @Parameter(name = "isLike", description = "좋아요 -> true 취소 -> false")
    })
    @Operation(summary = "Board 좋아요")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "정상 작동", content = @Content()),
            @ApiResponse(responseCode = "403", description = "권한 없음", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Board 정보 없음", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/like/{id}")
    public ResponseEntity<Void> BoardLike(
            @PathVariable Long id,
            @RequestParam Long userId,
            @RequestParam(defaultValue = "true") Boolean isLike)
    {
        boardService.boardLike(id ,userId, isLike);
        return ResponseEntity.ok().build();
    }
}
