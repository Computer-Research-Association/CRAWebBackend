package com.handong.cra.crawebbackend.item.controller;


import com.handong.cra.crawebbackend.auth.domain.CustomUserDetails;
import com.handong.cra.crawebbackend.item.dto.CreateItemDto;
import com.handong.cra.crawebbackend.item.dto.UpdateItemDto;
import com.handong.cra.crawebbackend.item.dto.request.ReqCreateItemDto;
import com.handong.cra.crawebbackend.item.dto.request.ReqUpdateItemDto;
import com.handong.cra.crawebbackend.item.dto.response.ResAdminDetailItemDto;
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
    public ResponseEntity<ResCreateItemDto> createItem(
            @AuthenticationPrincipal final CustomUserDetails customUserDetails,
            @RequestBody final ReqCreateItemDto reqCreateItemDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ResCreateItemDto.from(
                itemService.createItem(CreateItemDto.of(customUserDetails.getUserId(), reqCreateItemDto))));
    }

    @PutMapping("/{itemId}")
    public ResponseEntity<ResUpdateItemDto> updateItem(
            @AuthenticationPrincipal final CustomUserDetails customUserDetails,
            @PathVariable final Long itemId,
            @RequestBody final ReqUpdateItemDto reqUpdateItemDto) {

        return ResponseEntity.ok(ResUpdateItemDto.from(
                itemService.updateItem(
                        UpdateItemDto.of(customUserDetails.getUserId(), itemId, reqUpdateItemDto))));
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<ResAdminDetailItemDto> getAdminItem(@PathVariable final Long itemId) {
        return ResponseEntity
                .ok(ResAdminDetailItemDto.from(itemService.getDetailById(itemId)));
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> deleteItemById(
            @AuthenticationPrincipal final CustomUserDetails customUserDetails,
            @PathVariable final Long itemId) {
        itemService.deleteItemById(UpdateItemDto.of(itemId, customUserDetails.getUserId()));
        return ResponseEntity.ok().build();
    }

    @PutMapping("/valid/{itemId}")
    public ResponseEntity<Void> changeValidatingById(
            @AuthenticationPrincipal final CustomUserDetails customUserDetails,
            @PathVariable final Long itemId,
            @RequestParam final Boolean valid,
            @RequestParam final String username) {
        itemService.changeValidatingById(
                UpdateItemDto.of(itemId, customUserDetails.getUserId(), valid, username));
        return ResponseEntity.ok().build();
    }
}
