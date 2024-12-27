package com.handong.cra.crawebbackend.havruta.controller;

import com.handong.cra.crawebbackend.havruta.dto.CreateHavrutaDto;
import com.handong.cra.crawebbackend.havruta.dto.CreatehavrutaDto;
import com.handong.cra.crawebbackend.havruta.dto.ListHavrutaDto;
import com.handong.cra.crawebbackend.havruta.dto.request.ReqCreateHavrutaDto;
import com.handong.cra.crawebbackend.havruta.dto.response.ResCreateHarvrutaDto;
import com.handong.cra.crawebbackend.havruta.dto.response.ResCreateHavrutaDto;
import com.handong.cra.crawebbackend.havruta.dto.response.ResDetailHavrutaDto;
import com.handong.cra.crawebbackend.havruta.dto.response.ResListHavrutaDto;
import com.handong.cra.crawebbackend.havruta.dto.response.ResUpdateHavrutaDto;
import com.handong.cra.crawebbackend.havruta.service.HavrutaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/*
 * 관리자 페이지용 api
 * */

@RestController("/api/havruta")
@RequiredArgsConstructor
// TODO 관리자에게만 호출 받을 수 있도록 할 것!
public class HavrutaController {

    private final HavrutaService havrutaService;


    @GetMapping("")
    public ResponseEntity<List<ResListHavrutaDto>> getHavrutaList() {
        List<ListHavrutaDto> dtos = havrutaService.getListHavruta();
        List<ResListHavrutaDto> resListHavrutaDtos = dtos.stream().map(ResListHavrutaDto::new).toList();
        return ResponseEntity.ok().body(resListHavrutaDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResDetailHavrutaDto> detailedHavruta(PathVariable Long id){


        ResDetailHavrutaDto resDetailHavrutaDto = new ResDetailHavrutaDto();

        return ResponseEntity.ok().body()

    }

    @PostMapping("")
    public ResponseEntity<ResCreateHavrutaDto> createHavruta(@RequestBody ReqCreateHavrutaDto reqCreateHavrutaDto) {
        CreateHavrutaDto createhavrutaDto = havrutaService.createHavruta(reqCreateHavrutaDto);
        ResCreateHavrutaDto resCreateHarbrutaDto = new ResCreateHavrutaDto(createhavrutaDto);
        return ResponseEntity.status(201).body(resCreateHarbrutaDto);
    }


    @PutMapping("/{id}")
    public ResponseEntity<ResUpdateHavrutaDto> updateHavruta(@PathVariable Long id, @RequestBody ReqCreateHavrutaDto reqUpdateHavrutaDto) {




        return ResponseEntity.ok().body(null);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHavruta(@PathVariable Long id ){

        return ResponseEntity.ok().build();
    }


}
