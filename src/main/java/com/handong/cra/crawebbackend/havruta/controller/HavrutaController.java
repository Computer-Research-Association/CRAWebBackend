package com.handong.cra.crawebbackend.havruta.controller;


import com.handong.cra.crawebbackend.havruta.dto.response.ResListHavrutaDto;
import com.handong.cra.crawebbackend.havruta.service.HavrutaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/havruta")
@RequiredArgsConstructor
@Slf4j
public class HavrutaController {
    private final HavrutaService havrutaService;

    @GetMapping("")
    public ResponseEntity<List<ResListHavrutaDto>> getHavrutas() {
        return ResponseEntity.ok().body(havrutaService.getAllHavrutas().stream().map(ResListHavrutaDto::from).toList());
    }
}
