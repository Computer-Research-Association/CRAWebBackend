package com.handong.cra.crawebbackend.item.controller;


import com.handong.cra.crawebbackend.item.dto.CreateItemDto;
import com.handong.cra.crawebbackend.item.dto.UpdateItemDto;
import com.handong.cra.crawebbackend.item.dto.request.ReqCreateItemDto;
import com.handong.cra.crawebbackend.item.dto.request.ReqUpdateItemDto;
import com.handong.cra.crawebbackend.item.dto.response.ResCreateItemDto;
import com.handong.cra.crawebbackend.item.dto.response.ResUpdateItemDto;
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
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResCreateItemDto.from(itemService.createItem(CreateItemDto.from(reqCreateItemDto))));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResUpdateItemDto> updateItem(@PathVariable Long id, @RequestBody ReqUpdateItemDto reqUpdateItemDto) {
        return ResponseEntity
                .ok(ResUpdateItemDto.from(itemService.updateItem(id, UpdateItemDto.from(reqUpdateItemDto))));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItemById(@PathVariable Long id) {
        itemService.deleteItemById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("valid/{id}")
    public ResponseEntity<Void> changeValidatingById(@PathVariable Long id, @RequestParam Boolean valid) {
        itemService.changeValidatingById(id, valid);
        return ResponseEntity.ok().build();
    }
}
