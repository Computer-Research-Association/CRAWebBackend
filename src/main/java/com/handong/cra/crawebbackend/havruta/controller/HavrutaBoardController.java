package com.handong.cra.crawebbackend.havruta.controller;

import com.handong.cra.crawebbackend.havruta.dto.havrutaboard.CreateHavrutaBoardDto;
import com.handong.cra.crawebbackend.havruta.dto.havrutaboard.DetailHavrutaBoardDto;
import com.handong.cra.crawebbackend.havruta.dto.havrutaboard.ListHavrutaBoardDto;
import com.handong.cra.crawebbackend.havruta.dto.havrutaboard.UpdateHavrutaBoardDto;
import com.handong.cra.crawebbackend.havruta.dto.havrutaboard.request.ReqCreateHavrutaBoardDto;
import com.handong.cra.crawebbackend.havruta.dto.havrutaboard.request.ReqUpdateHavrutaBoardDto;
import com.handong.cra.crawebbackend.havruta.dto.havrutaboard.response.ResCreateHavrutaBoardDto;
import com.handong.cra.crawebbackend.havruta.dto.havrutaboard.response.ResDetailHavrutaBoardDto;
import com.handong.cra.crawebbackend.havruta.dto.havrutaboard.response.ResListHavrutaBoardDto;
import com.handong.cra.crawebbackend.havruta.dto.havrutaboard.response.ResUpdateHavrutaBoardDto;
import com.handong.cra.crawebbackend.havruta.service.HavrutaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/board/havruta")
@RequiredArgsConstructor
@Slf4j
public class HavrutaBoardController {

    private final HavrutaService havrutaService;
    @GetMapping("/{id}")
    public ResponseEntity<List<ResListHavrutaBoardDto>> getHavrutaBoardsByHavrutaId(@PathVariable Long id){
        return ResponseEntity.ok().body(havrutaService.getHavrutaBoardsByHavrutaId(id).stream().map(ResListHavrutaBoardDto::from).toList());
    }

    @GetMapping("/view/{id}")
    public ResponseEntity<ResDetailHavrutaBoardDto> getDetailHavrutaBoardByBoardId(@PathVariable Long id){
        ResDetailHavrutaBoardDto resDetailHavrutaBoardDto = ResDetailHavrutaBoardDto.from(havrutaService.getDetailHavrutaBoardByBoardId(id));
        return ResponseEntity.ok(resDetailHavrutaBoardDto);
    }

    @PostMapping("")
    public ResponseEntity<ResCreateHavrutaBoardDto> createHavrutaBoard(@RequestBody ReqCreateHavrutaBoardDto reqCreateHavrutaBoardDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(ResCreateHavrutaBoardDto.from(havrutaService.createHavrutaBoard(CreateHavrutaBoardDto.from(reqCreateHavrutaBoardDto))));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResUpdateHavrutaBoardDto> updateHavrutaBoard(@PathVariable Long id, @RequestBody ReqUpdateHavrutaBoardDto requpdateHavrutaBoardDto){
        ResUpdateHavrutaBoardDto resUpdateHavrutaBoardDto;
        resUpdateHavrutaBoardDto = ResUpdateHavrutaBoardDto.from(havrutaService.updateHavrutaBoard(id, UpdateHavrutaBoardDto.of(requpdateHavrutaBoardDto)));
        return ResponseEntity.ok().body(resUpdateHavrutaBoardDto);
    }
}
