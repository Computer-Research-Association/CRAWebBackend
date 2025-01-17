package com.handong.cra.crawebbackend.item.controller;

import com.handong.cra.crawebbackend.exception.item.ItemIllegalCategoryException;
import com.handong.cra.crawebbackend.exception.item.ItemPageSizeLimitExceededException;
import com.handong.cra.crawebbackend.item.domain.ItemCategory;
import com.handong.cra.crawebbackend.item.dto.response.ResDetailItemDto;
import com.handong.cra.crawebbackend.item.dto.response.ResListItemDto;
import com.handong.cra.crawebbackend.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/item")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;
    private final Integer MAX_PAGE_SIZE = 100;

    @GetMapping("/{category}")
    public ResponseEntity<List<ResListItemDto>> getAllItemsByCategory(@PathVariable Integer category) {
        if (category < 0 || category >= ItemCategory.values().length) {
            throw new ItemIllegalCategoryException();
        }
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
        if (perPage > MAX_PAGE_SIZE) {
            throw new ItemPageSizeLimitExceededException();
        }
        return ResponseEntity.ok(itemService.
                getPaginationItem(page, perPage, isASC, ItemCategory.values()[category]).stream().map(ResListItemDto::from).toList());
    }

    @GetMapping("/view/{id}")
    public ResponseEntity<ResDetailItemDto> getDetailItems(@PathVariable Long id) {
        return ResponseEntity.ok(ResDetailItemDto.from(itemService.getDetailById(id)));
    }

}
