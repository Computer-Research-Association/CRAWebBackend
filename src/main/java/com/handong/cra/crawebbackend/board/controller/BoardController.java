package com.handong.cra.crawebbackend.board.controller;

import com.handong.cra.crawebbackend.board.domain.Board;
import com.handong.cra.crawebbackend.board.domain.Category;
import com.handong.cra.crawebbackend.board.dto.CreateBoardDto;
import com.handong.cra.crawebbackend.board.dto.request.ReqCreateBoardDto;
import com.handong.cra.crawebbackend.board.dto.request.ReqUpdateBoardDto;
import com.handong.cra.crawebbackend.board.dto.response.ResCreateBoardDto;
import com.handong.cra.crawebbackend.board.dto.response.ResDetailBoardDto;
import com.handong.cra.crawebbackend.board.dto.response.ResListBoardDto;
import com.handong.cra.crawebbackend.board.dto.response.ResUpdateBoardDto;
import com.handong.cra.crawebbackend.board.service.BoardService;
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

        // service dto => response dto
        List<ResListBoardDto> lists;
        lists = boardService.getBoardsByCategory(Category.values()[category] /*int to Enum*/).
                stream().map(ResListBoardDto::new).toList();

        return ResponseEntity.ok().body(lists);
    }

    @PostMapping("")
    public ResponseEntity<ResCreateBoardDto> createBoard(@RequestBody ReqCreateBoardDto reqCreateBoardDto) {

        // service dto => response dto
        ResCreateBoardDto resCreateBoardDto;
        resCreateBoardDto = new ResCreateBoardDto(boardService.createBoard(CreateBoardDto.of(reqCreateBoardDto, reqCreateBoardDto.getUserId())));

        // 201 return
        return ResponseEntity.status(HttpStatus.CREATED).body(resCreateBoardDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateBoard(@PathVariable Long id, @RequestBody ReqUpdateBoardDto reqUpdateBoardDto) {
        return null;
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBoard(@PathVariable Long id) {
        return null;
//        // success
//        if (boardService.deleteBoardById(id))
//            return ResponseEntity.ok().build();
//
//        // fail
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
