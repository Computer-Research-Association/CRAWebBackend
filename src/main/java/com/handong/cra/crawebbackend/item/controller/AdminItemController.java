package com.handong.cra.crawebbackend.item.controller;


import com.handong.cra.crawebbackend.item.dto.CreateItemDto;
import com.handong.cra.crawebbackend.item.dto.request.ReqCreateItemDto;
import com.handong.cra.crawebbackend.item.dto.response.ResCreateItemDto;
import com.handong.cra.crawebbackend.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/item")
@RequiredArgsConstructor
public class AdminItemController {

    private final ItemService itemService;

    @PostMapping("")
    public ResponseEntity<ResCreateItemDto> createItem(@RequestBody ReqCreateItemDto reqCreateItemDto) {
        CreateItemDto requestDto = CreateItemDto.from(reqCreateItemDto);
        CreateItemDto responseDto = itemService.createItem(requestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(ResCreateItemDto.from(responseDto));
    }
}
