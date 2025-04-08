package com.handong.cra.crawebbackend.item.controller;

import com.handong.cra.crawebbackend.exception.item.ItemPageSizeLimitExceededException;
import com.handong.cra.crawebbackend.item.domain.ItemCategory;
import com.handong.cra.crawebbackend.item.dto.PageItemDataDto;
import com.handong.cra.crawebbackend.item.dto.response.ResDetailItemDto;
import com.handong.cra.crawebbackend.item.dto.response.ResPageItemDto;
import com.handong.cra.crawebbackend.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/item")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @Value("${spring.data.page.MAX_PER_PAGE}")
    private Integer MAX_PAGE_SIZE;

    @GetMapping("/{category}/page")
    public ResponseEntity<ResPageItemDto> getPageListItem(
            @PathVariable final Integer category,
            @RequestParam final Long page,
            @RequestParam(required = false, defaultValue = "10") final Integer perPage,
            @RequestParam(required = false, defaultValue = "true") final Boolean isASC
    ) {
        if (perPage > MAX_PAGE_SIZE) {
            throw new ItemPageSizeLimitExceededException();
        }
        final PageItemDataDto pageItemDataDto = PageItemDataDto.builder()
                .page(page)
                .perPage(perPage)
                .isASC(isASC)
                .itemCategory(ItemCategory.values()[category]).build();
        return ResponseEntity.ok(ResPageItemDto.from(itemService.getPaginationItem(pageItemDataDto)));
    }

    @GetMapping("/view/{id}")
    public ResponseEntity<ResDetailItemDto> getDetailItems(@PathVariable final Long id) {
        return ResponseEntity.ok(ResDetailItemDto.from(itemService.getDetailById(id)));
    }

}
