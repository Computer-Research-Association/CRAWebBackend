package com.handong.cra.crawebbackend.havruta.controller;

import com.handong.cra.crawebbackend.havruta.dto.CreateHavrutaDto;
import com.handong.cra.crawebbackend.havruta.dto.UpdateHavrutaDto;
import com.handong.cra.crawebbackend.havruta.dto.request.ReqCreateHavrutaDto;
import com.handong.cra.crawebbackend.havruta.dto.request.ReqUpdateHavrutaDto;
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

    @GetMapping
    public ResponseEntity<List<ResListHavrutaDto>> getHavrutas() {
        return ResponseEntity.ok().body(havrutaService.getAllHavrutas().stream().map(ResListHavrutaDto::from).toList());
    }

    @GetMapping("/view/{id}")
    public ResponseEntity<ResDetailHavrutaDto> getHavrutaById(@PathVariable Long id) {
        ResDetailHavrutaDto resDetailHavrutaDto = ResDetailHavrutaDto.from(havrutaService.getHavrutaById(id));

        if(resDetailHavrutaDto == null) return ResponseEntity.notFound().build();

        else return ResponseEntity.ok().body(resDetailHavrutaDto);
    }

    @PostMapping("")
    public ResponseEntity<ResCreateHavrutaDto> createHavruta(@RequestBody ReqCreateHavrutaDto reqCreateHavrutaDto) {
        CreateHavrutaDto createHavrutaDto = CreateHavrutaDto.from(reqCreateHavrutaDto);

        ResCreateHavrutaDto response = ResCreateHavrutaDto.from(havrutaService.createHavruta(createHavrutaDto));

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResUpdateHavrutaDto> updateHavruta(@PathVariable Long id, @RequestBody ReqUpdateHavrutaDto reqUpdateHavrutaDto) {
        ResUpdateHavrutaDto resUpdateHavrutaDto = ResUpdateHavrutaDto.from(havrutaService.updateHavruta(UpdateHavrutaDto.of(id, reqUpdateHavrutaDto)));;

        return ResponseEntity.ok().body(resUpdateHavrutaDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHavruta(@PathVariable Long id) {
        if (havrutaService.deleteHavruta(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
