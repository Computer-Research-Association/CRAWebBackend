package com.handong.cra.crawebbackend.board.controller;


import com.handong.cra.crawebbackend.board.domain.Category;
import com.handong.cra.crawebbackend.board.dto.BoardPinDto;
import com.handong.cra.crawebbackend.board.dto.request.ReqBoardPinDto;
import com.handong.cra.crawebbackend.board.dto.response.ResBoardPinDto;
import com.handong.cra.crawebbackend.board.service.BoardPinService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/board/pin")
public class BoardPinAdminController {

    private final BoardPinService boardPinService;

    @PostMapping("")
    public ResponseEntity<ResBoardPinDto> setPin(@RequestBody final ReqBoardPinDto reqBoardPinDto) {
        return ResponseEntity.ok(ResBoardPinDto.from(boardPinService.setPin(BoardPinDto.from(reqBoardPinDto))));
    }

    @DeleteMapping("/{pinId}")
    public ResponseEntity<Void> removePin(@PathVariable final Long pinId) {
        boardPinService.removePinById(pinId);
        return ResponseEntity.ok().build();

    }

    // admin page
    @GetMapping("/{category}")
    public ResponseEntity<List<ResBoardPinDto>> getPins(@PathVariable final Integer category) {
        return ResponseEntity.ok(boardPinService.getPinByBoardCategory(Category.values()[category]).stream().map(ResBoardPinDto::from).toList());

    }

    // 최대 보드 카테고리 * 5 개의 데이터 전송 -> paging 필요 없을듯?
    @GetMapping("")
    public ResponseEntity<List<ResBoardPinDto>> getAllPins() {
        return ResponseEntity.ok(boardPinService.getALlPins().stream().map(ResBoardPinDto::from).toList());
    }
}
