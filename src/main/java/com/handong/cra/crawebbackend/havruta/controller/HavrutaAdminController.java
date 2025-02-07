package com.handong.cra.crawebbackend.havruta.controller;

import com.handong.cra.crawebbackend.auth.domain.CustomUserDetails;
import com.handong.cra.crawebbackend.havruta.dto.CreateHavrutaDto;
import com.handong.cra.crawebbackend.havruta.dto.UpdateHavrutaDto;
import com.handong.cra.crawebbackend.havruta.dto.request.ReqCreateHavrutaDto;
import com.handong.cra.crawebbackend.havruta.dto.request.ReqUpdateHavrutaDto;
import com.handong.cra.crawebbackend.havruta.dto.response.ResCreateHavrutaDto;
import com.handong.cra.crawebbackend.havruta.dto.response.ResDetailHavrutaDto;
import com.handong.cra.crawebbackend.havruta.dto.response.ResListHavrutaDto;
import com.handong.cra.crawebbackend.havruta.dto.response.ResUpdateHavrutaDto;
import com.handong.cra.crawebbackend.havruta.service.HavrutaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/havruta")
@RequiredArgsConstructor
public class HavrutaAdminController {
    private final HavrutaService havrutaService;


    @GetMapping("/view/{havrutaId}")
    public ResponseEntity<ResDetailHavrutaDto> getHavrutaById(@PathVariable Long havrutaId) {
        ResDetailHavrutaDto resDetailHavrutaDto = ResDetailHavrutaDto.from(havrutaService.getHavrutaById(havrutaId));

        if (resDetailHavrutaDto == null) return ResponseEntity.notFound().build();

        else return ResponseEntity.ok().body(resDetailHavrutaDto);
    }

    @PostMapping("")
    public ResponseEntity<ResCreateHavrutaDto> createHavruta(@RequestBody ReqCreateHavrutaDto reqCreateHavrutaDto) {
        CreateHavrutaDto createHavrutaDto = CreateHavrutaDto.from(reqCreateHavrutaDto);

        ResCreateHavrutaDto response = ResCreateHavrutaDto.from(havrutaService.createHavruta(createHavrutaDto));

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{havrutaId}")
    public ResponseEntity<ResUpdateHavrutaDto> updateHavruta(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable Long havrutaId, @RequestBody ReqUpdateHavrutaDto reqUpdateHavrutaDto) {
        ResUpdateHavrutaDto resUpdateHavrutaDto = ResUpdateHavrutaDto.from(havrutaService.updateHavruta(UpdateHavrutaDto.of(havrutaId, customUserDetails.getUserId(), reqUpdateHavrutaDto)));
        ;

        return ResponseEntity.ok().body(resUpdateHavrutaDto);
    }

    @DeleteMapping("/{havrutaId}")
    public ResponseEntity<Void> deleteHavruta(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable Long havrutaId) {
        if (havrutaService.deleteHavruta(UpdateHavrutaDto.of(havrutaId, customUserDetails.getUserId(), true))) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
