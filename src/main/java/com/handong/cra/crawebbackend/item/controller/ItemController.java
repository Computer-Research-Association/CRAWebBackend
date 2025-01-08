package com.handong.cra.crawebbackend.item.controller;

import com.handong.cra.crawebbackend.board.domain.Board;
import com.handong.cra.crawebbackend.board.dto.ListBoardDto;
import com.handong.cra.crawebbackend.item.domain.ItemCategory;
import com.handong.cra.crawebbackend.item.dto.CreateItemDto;
import com.handong.cra.crawebbackend.item.dto.request.ReqCreateItemDto;
import com.handong.cra.crawebbackend.item.dto.response.ResCreateItemDto;
import com.handong.cra.crawebbackend.item.dto.response.ResDetailItemDto;
import com.handong.cra.crawebbackend.item.dto.response.ResListItemDto;
import com.handong.cra.crawebbackend.item.repository.ItemRepository;
import com.handong.cra.crawebbackend.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/item")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @GetMapping("/{category}")
    public ResponseEntity<List<ResListItemDto>> getAllItemsByCategory(@PathVariable Integer category) {
        return ResponseEntity.ok(itemService.
                getItemsByCategory(ItemCategory.values()[category]).stream().map(ResListItemDto::from).toList());
    }


    @GetMapping("/{category}/page")
    public ResponseEntity<List<ResListItemDto>> getPageListItem(
            @PathVariable Integer category,
            @RequestParam Long page,
            @RequestParam(required = false, defaultValue = "10") Integer perPage,
            @RequestParam(required = false, defaultValue = "true") Boolean isASC
    ) {

        return ResponseEntity.ok(itemService.
                getPaginationItem(page, perPage, isASC, ItemCategory.values()[category]).stream().map(ResListItemDto::from).toList());
    }

    @GetMapping("/view/{id}")
    public ResponseEntity<ResDetailItemDto> getDetailItems(@PathVariable Long id) {
        return ResponseEntity.ok(ResDetailItemDto.from(itemService.getDetailById(id)));
    }

}
