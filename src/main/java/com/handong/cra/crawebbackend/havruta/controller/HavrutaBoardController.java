package com.handong.cra.crawebbackend.havruta.controller;


import com.handong.cra.crawebbackend.havruta.dto.havrutaboard.CreateHavrutaBoardDto;
import com.handong.cra.crawebbackend.havruta.dto.havrutaboard.UpdateHavrutaBoardDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class HavrutaBoardController {

    @GetMapping("")
    public ResponseEntity<List<ResListHavrutaBoardDto>> getHavrutaBoardsByHavrutaId(Long id){
        return null;
    }

    @GetMapping("")
    public ResponseEntity<ResDetailHavrutaBoardDto> getDetailHavrutaBoardByBoardId(Long id){
        return null;
    }

    @PostMapping("")
    public ResponseEntity<ResCreateHavrutaBoardDto> createHavrutaBoard(CreateHavrutaBoardDto createHavrutaBoardDto){
        return null;
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
