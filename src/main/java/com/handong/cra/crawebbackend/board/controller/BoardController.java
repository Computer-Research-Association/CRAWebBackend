package com.handong.cra.crawebbackend.board.controller;

import com.handong.cra.crawebbackend.board.domain.Board;
import com.handong.cra.crawebbackend.board.domain.Category;
import com.handong.cra.crawebbackend.board.dto.request.ReqCreateBoardDto;
import com.handong.cra.crawebbackend.board.dto.response.ResCreateBoardDto;
import com.handong.cra.crawebbackend.board.dto.response.ResDetailBoardDto;
import com.handong.cra.crawebbackend.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @GetMapping("/{category}")
    public ResponseEntity<List<ResDetailBoardDto>> getBoardsByCategory(@PathVariable Integer category) {
        return ResponseEntity.ok().body(boardService.getBoardsByCategory(Category.values()[category]));
    }


    @PostMapping("")
    public ResponseEntity<ResCreateBoardDto> createBoard(@RequestBody ReqCreateBoardDto reqCreateBoardDto) {
        return ResponseEntity.ok().body(boardService.createBoard(reqCreateBoardDto));
    }
}
