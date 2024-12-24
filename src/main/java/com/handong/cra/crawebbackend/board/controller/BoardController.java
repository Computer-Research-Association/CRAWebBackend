package com.handong.cra.crawebbackend.board.controller;

import com.handong.cra.crawebbackend.board.domain.Board;
import com.handong.cra.crawebbackend.board.domain.Category;
import com.handong.cra.crawebbackend.board.dto.ReqCreateBoardDto;
import com.handong.cra.crawebbackend.board.dto.ResCreateBoardDto;
import com.handong.cra.crawebbackend.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @GetMapping("/{category}")
    public List<Board> getBoardsByCategory(@PathVariable Category category) {
        return boardService.getBoardsByCategory(category);
    }

    @PostMapping("")
    public ResCreateBoardDto createBoard(@RequestBody ReqCreateBoardDto reqCreateBoardDto) {
        return boardService.createBoard(reqCreateBoardDto);

    }
}
