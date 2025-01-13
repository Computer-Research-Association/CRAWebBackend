package com.handong.cra.crawebbackend.havruta.controller;


import com.handong.cra.crawebbackend.havruta.dto.havrutaboard.CreateHavrutaBoardDto;
import com.handong.cra.crawebbackend.havruta.dto.havrutaboard.DetailHavrutaBoardDto;
import com.handong.cra.crawebbackend.havruta.dto.havrutaboard.UpdateHavrutaBoardDto;
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
@RequestMapping("/api/havruta")
@RequiredArgsConstructor
@Slf4j
public class HavrutaBoardController {


    private final HavrutaService havrutaService;
    @GetMapping("")
    public ResponseEntity<List<ResListHavrutaBoardDto>> getHavrutaBoardsByHavrutaId(Long id){
        return null;
    }

//    @GetMapping("")
//    public ResponseEntity<ResDetailHavrutaBoardDto> getDetailHavrutaBoardByBoardId(Long id){
//        return null;
//    }
    @GetMapping("/view/{id}")
    public ResponseEntity<DetailHavrutaBoardDto> getDetailHavrutaBoardByBoardId(@PathVariable Long id){
        return ResponseEntity.ok(havrutaService.getDetailHavrutaBoardByBoardId(id));
    }

//    @PostMapping("")
//    public ResponseEntity<ResCreateHavrutaBoardDto> createHavrutaBoard(ReqCreateHavrutaBoardDto reqcreateHavrutaBoardDto){
//        return null;
//    }
    @PostMapping("")
    public ResponseEntity<CreateHavrutaBoardDto> createHavrutaBoard(@RequestBody CreateHavrutaBoardDto createHavrutaBoardDto){
        log.info("test here user id = {}", createHavrutaBoardDto.getUserId());
        return ResponseEntity.status(HttpStatus.CREATED).body(havrutaService.createHavrutaBoard(createHavrutaBoardDto));
    }

    @PutMapping("")
    public ResponseEntity<ResUpdateHavrutaBoardDto> updateHavrutaBoard(UpdateHavrutaBoardDto updateHavrutaBoardDto){
        return null;
    }

    @DeleteMapping("")
    public ResponseEntity<Void> deleteHavrutaBoardById(Long id){
        return null;
    }


}
