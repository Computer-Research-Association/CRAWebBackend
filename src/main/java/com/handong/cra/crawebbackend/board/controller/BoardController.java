package com.handong.cra.crawebbackend.board.controller;

import com.handong.cra.crawebbackend.board.domain.Category;
import com.handong.cra.crawebbackend.board.dto.CreateBoardDto;
import com.handong.cra.crawebbackend.board.dto.UpdateBoardDto;
import com.handong.cra.crawebbackend.board.dto.request.ReqCreateBoardDto;
import com.handong.cra.crawebbackend.board.dto.request.ReqUpdateBoardDto;
import com.handong.cra.crawebbackend.board.dto.response.ResCreateBoardDto;
import com.handong.cra.crawebbackend.board.dto.response.ResDetailBoardDto;
import com.handong.cra.crawebbackend.board.dto.response.ResListBoardDto;
import com.handong.cra.crawebbackend.board.dto.response.ResUpdateBoardDto;
import com.handong.cra.crawebbackend.board.service.BoardService;
import com.handong.cra.crawebbackend.board.service.BoardServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @GetMapping("/{category}")
    public ResponseEntity<List<ResListBoardDto>> getBoardsByCategory(@PathVariable Integer category) {
        return ResponseEntity.ok().body(boardService.getBoardsByCategory(Category.values()[category] /*int to Enum*/)
                .stream().map(ResListBoardDto::from).toList());
    }

    @GetMapping("/view/{id}")
    public ResponseEntity<ResDetailBoardDto> getDetailBoard(@PathVariable Long id){
        ResDetailBoardDto resDetailBoardDto = ResDetailBoardDto.from(boardService.getDetailBoardById(id));

        // deleted
        if (resDetailBoardDto== null) return  ResponseEntity.notFound().build();

        else return ResponseEntity.ok().body(resDetailBoardDto);
    }

    @PostMapping("")
    public ResponseEntity<ResCreateBoardDto> createBoard(@RequestBody ReqCreateBoardDto reqCreateBoardDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ResCreateBoardDto.from(boardService
                .createBoard(CreateBoardDto.of(reqCreateBoardDto, reqCreateBoardDto.getUserId()))));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResUpdateBoardDto> updateBoard(@PathVariable Long id, @RequestBody ReqUpdateBoardDto reqUpdateBoardDto) {
        ResUpdateBoardDto resUpdateBoardDto;
        resUpdateBoardDto =ResUpdateBoardDto.from(boardService.updateBoard(UpdateBoardDto.of(id, reqUpdateBoardDto)));
        return ResponseEntity.ok().body(resUpdateBoardDto);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBoard(@PathVariable Long id) {
        // success
        if (boardService.deleteBoardById(id))
            return ResponseEntity.ok().build();

        // fail
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
