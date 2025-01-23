package com.handong.cra.crawebbackend.havruta.controller;

import com.handong.cra.crawebbackend.board.domain.BoardOrderBy;
import com.handong.cra.crawebbackend.havruta.dto.havrutaboard.CreateHavrutaBoardDto;
import com.handong.cra.crawebbackend.havruta.dto.havrutaboard.ListHavrutaBoardDto;
import com.handong.cra.crawebbackend.havruta.dto.havrutaboard.UpdateHavrutaBoardDto;
import com.handong.cra.crawebbackend.havruta.dto.havrutaboard.request.ReqCreateHavrutaBoardDto;
import com.handong.cra.crawebbackend.havruta.dto.havrutaboard.request.ReqUpdateHavrutaBoardDto;
import com.handong.cra.crawebbackend.havruta.dto.havrutaboard.response.ResCreateHavrutaBoardDto;
import com.handong.cra.crawebbackend.havruta.dto.havrutaboard.response.ResDetailHavrutaBoardDto;
import com.handong.cra.crawebbackend.havruta.dto.havrutaboard.response.ResListHavrutaBoardDto;
import com.handong.cra.crawebbackend.havruta.dto.havrutaboard.response.ResUpdateHavrutaBoardDto;
import com.handong.cra.crawebbackend.havruta.service.HavrutaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/board/havruta")
@RequiredArgsConstructor
@Slf4j
public class HavrutaBoardController {

    private final HavrutaService havrutaService;
    @GetMapping("")
    public ResponseEntity<List<ResListHavrutaBoardDto>> getHavrutaBoards(){
        return ResponseEntity.ok().body(havrutaService.getHavrutaBoards().stream().map(ResListHavrutaBoardDto::from).toList());
    }

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
    public ResponseEntity<ResCreateHavrutaBoardDto> createHavrutaBoard(@Valid @RequestPart("board") ReqCreateHavrutaBoardDto reqCreateHavrutaBoardDto,
                                                                       @RequestPart("files")List<MultipartFile> files){
        return ResponseEntity.status(HttpStatus.CREATED).body(ResCreateHavrutaBoardDto.from(havrutaService.createHavrutaBoard(CreateHavrutaBoardDto.of(reqCreateHavrutaBoardDto, reqCreateHavrutaBoardDto.getUserId(), files))));
    }

    @GetMapping("/page/{page}")
    public ResponseEntity<List<ResListHavrutaBoardDto>> getPaginationAllHavrutaBoard(
            @PathVariable Long page,
            @RequestParam(required = false, defaultValue = "0") Integer perPage,
            @RequestParam(required = false, defaultValue = "0") Integer orderBy,
            @RequestParam(required = false, defaultValue = "true") Boolean isASC
    ){
        List<ListHavrutaBoardDto> listHavrutaBoardDtos = havrutaService.getPaginationAllHavrutaBoard(page, perPage, BoardOrderBy.values()[orderBy], isASC);
        return ResponseEntity.ok(listHavrutaBoardDtos.stream().map(ResListHavrutaBoardDto::from).toList());
    }

    @GetMapping("/{id}/page/{page}")
    public ResponseEntity<List<ResListHavrutaBoardDto>> getPaginationHavrutaBoard(
            @PathVariable Long id,
            @PathVariable Long page,
            @RequestParam(required = false, defaultValue = "0") Integer perPage,
            @RequestParam(required = false, defaultValue = "0") Integer orderBy,
            @RequestParam(required = false, defaultValue = "true") Boolean isASC
    ){
        List<ListHavrutaBoardDto> listHavrutaBoardDtos = havrutaService.getPaginationHavrutaBoard(id, page, perPage, BoardOrderBy.values()[orderBy], isASC);
        return ResponseEntity.ok(listHavrutaBoardDtos.stream().map(ResListHavrutaBoardDto::from).toList());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResUpdateHavrutaBoardDto> updateHavrutaBoard(@PathVariable Long id,
                                                                       @RequestPart("board") ReqUpdateHavrutaBoardDto requpdateHavrutaBoardDto,
                                                                       @RequestPart(value = "files") List<MultipartFile> files){
        ResUpdateHavrutaBoardDto resUpdateHavrutaBoardDto;
        resUpdateHavrutaBoardDto = ResUpdateHavrutaBoardDto.from(havrutaService.updateHavrutaBoard(UpdateHavrutaBoardDto.of(id, requpdateHavrutaBoardDto, files)));
        return ResponseEntity.ok().body(resUpdateHavrutaBoardDto);
    }
}
