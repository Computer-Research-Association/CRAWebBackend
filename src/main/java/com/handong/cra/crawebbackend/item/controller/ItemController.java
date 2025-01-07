package com.handong.cra.crawebbackend.item.controller;

import com.handong.cra.crawebbackend.item.dto.CreateItemDto;
import com.handong.cra.crawebbackend.item.dto.request.ReqCreateItemDto;
import com.handong.cra.crawebbackend.item.dto.response.ResCreateItemDto;
import com.handong.cra.crawebbackend.item.dto.response.ResListItemDto;
import com.handong.cra.crawebbackend.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/item")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @GetMapping("")
    public ResponseEntity<List<ResListItemDto>> getAllItems() {
//        return ResponseEntity.ok().body(itemService.getItemList().stream().map(ResListItemDto::from).toList());
        return null;
    }


    @GetMapping("/{id}")
    public ResponseEntity<List<ResListItemDto>> getDetailItems(@PathVariable Long id) {

//    return ResponseEntity.ok();
        return null;
    }

}
