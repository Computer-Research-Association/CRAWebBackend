package com.handong.cra.crawebbackend.havruta.controller;

import com.handong.cra.crawebbackend.havruta.dto.response.ResCreateHavrutaDto;
import com.handong.cra.crawebbackend.havruta.dto.response.ResListHavrutaDto;
import com.handong.cra.crawebbackend.havruta.dto.response.ResUpdateHavrutaDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/havruta")
@RequiredArgsConstructor
public class HavrutaController {

    @GetMapping("/")
    public ResponseEntity<List<ResListHavrutaDto>> getHavrutas() {

    }

    @GetMapping("/view/{id}")
    public ResponseEntity<ResListHavrutaDto> getHavrutaById(@PathVariable Long id) {

    }

    @PostMapping("")
    public ResponseEntity<ResCreateHavrutaDto> createHavruta(@RequestBody ResCreateHavrutaDto resCreateHavrutaDto) {

    }

    @PutMapping("/{id}")
    public ResponseEntity<ResUpdateHavrutaDto> updateHavruta(@PathVariable Long id, @RequestBody ResUpdateHavrutaDto resUpdateHavrutaDto) {

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHavruta(@PathVariable Long id) {

    }
}
