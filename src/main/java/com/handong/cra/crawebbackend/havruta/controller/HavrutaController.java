package com.handong.cra.crawebbackend.havruta.controller;

import com.handong.cra.crawebbackend.board.service.BoardServiceImpl;
import com.handong.cra.crawebbackend.havruta.dto.response.ResCreateHavrutaDto;
import com.handong.cra.crawebbackend.havruta.dto.response.ResDetailHavrutaDto;
import com.handong.cra.crawebbackend.havruta.dto.response.ResListHavrutaDto;
import com.handong.cra.crawebbackend.havruta.dto.response.ResUpdateHavrutaDto;
import com.handong.cra.crawebbackend.havruta.service.HavrutaService;
import com.handong.cra.crawebbackend.havruta.service.HavrutaServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/havruta")
@RequiredArgsConstructor
public class HavrutaController {
    private final HavrutaService havrutaService;
    private final HavrutaServiceImpl havrutaServiceImpl;

    @GetMapping
    public ResponseEntity<List<ResListHavrutaDto>> getHavrutas() {
        return ResponseEntity.ok().body(havrutaService.getAllHavrutas().stream().map(ResListHavrutaDto::from).toList());
    }

    @GetMapping("/view/{id}")
    public ResponseEntity<ResDetailHavrutaDto> getHavrutaById(@PathVariable Long id) {
        ResDetailHavrutaDto resDetailHavrutaDto = ResDetailHavrutaDto.from(havrutaServiceImpl.getHavrutaById(id));

        if(resDetailHavrutaDto == null) return ResponseEntity.notFound().build();

        else return ResponseEntity.ok().body(resDetailHavrutaDto);
    }
//
//    @PostMapping("")
//    public ResponseEntity<ResCreateHavrutaDto> createHavruta(@RequestBody ResCreateHavrutaDto resCreateHavrutaDto) {
//
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<ResUpdateHavrutaDto> updateHavruta(@PathVariable Long id, @RequestBody ResUpdateHavrutaDto resUpdateHavrutaDto) {
//
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteHavruta(@PathVariable Long id) {
//
//    }
}
