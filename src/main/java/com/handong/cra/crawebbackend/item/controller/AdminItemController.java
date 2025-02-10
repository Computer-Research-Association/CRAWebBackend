package com.handong.cra.crawebbackend.item.controller;


import com.handong.cra.crawebbackend.auth.domain.CustomUserDetails;
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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/item")
@RequiredArgsConstructor
public class AdminItemController {

    private final ItemService itemService;

    @PostMapping("")
    public ResponseEntity<ResCreateItemDto> createItem(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody ReqCreateItemDto reqCreateItemDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResCreateItemDto.from(itemService.createItem(CreateItemDto.of(customUserDetails.getUserId(), reqCreateItemDto))));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResUpdateItemDto> updateItem(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable Long id, @RequestBody ReqUpdateItemDto reqUpdateItemDto) {

        return ResponseEntity
                .ok(ResUpdateItemDto.from(itemService.updateItem(UpdateItemDto.of(customUserDetails.getUserId(), id, reqUpdateItemDto))));
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> deleteItemById(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable Long itemId) {
        itemService.deleteItemById(UpdateItemDto.of(itemId, customUserDetails.getUserId()));
        return ResponseEntity.ok().build();
    }

    @PutMapping("valid/{itemId}")
    public ResponseEntity<Void> changeValidatingById(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable Long itemId, @RequestParam Boolean valid) {
        itemService.changeValidatingById(UpdateItemDto.of(itemId, customUserDetails.getUserId(), valid));

        return ResponseEntity.ok().build();
    }
}
