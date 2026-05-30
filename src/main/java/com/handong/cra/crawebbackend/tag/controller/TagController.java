package com.handong.cra.crawebbackend.tag.controller;

import com.handong.cra.crawebbackend.board.domain.BoardOrderBy;
import com.handong.cra.crawebbackend.board.dto.PageBoardDataDto;
import com.handong.cra.crawebbackend.board.dto.response.ResPageBoardDto;
import com.handong.cra.crawebbackend.exception.board.PageSizeLimitExceededException;
import com.handong.cra.crawebbackend.tag.dto.request.ReqCreateTagDto;
import com.handong.cra.crawebbackend.tag.dto.request.ReqUpdateTagDto;
import com.handong.cra.crawebbackend.tag.dto.response.ResTagDto;
import com.handong.cra.crawebbackend.tag.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @Value("${spring.data.page.MAX_PER_PAGE}")
    private Integer MAX_PAGE_SIZE;

    @GetMapping
    public ResponseEntity<List<ResTagDto>> getAllTags() {
        return ResponseEntity.ok(tagService.getAllTags());
    }

    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> searchByTagName(@RequestParam String name) {
        Map<String, Object> result = new HashMap<>();
        result.put("boards", tagService.getBoardsByTagName(name));
        result.put("projects", tagService.getProjectsByTagName(name));
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResPageBoardDto> getTagById(
            @PathVariable Long id,
            @RequestParam(required = false, defaultValue = "0") final Long page,
            @RequestParam(required = false, defaultValue = "10") final Integer perPage,
            @RequestParam(required = false, defaultValue = "0") final Integer orderBy,
            @RequestParam(required = false, defaultValue = "true") final Boolean isASC
    ) {
        if (perPage > MAX_PAGE_SIZE) {
            throw new PageSizeLimitExceededException();
        }
        final PageBoardDataDto pageBoardDataDto = PageBoardDataDto.builder()
                .page(page)
                .perPage(perPage)
                .orderBy(BoardOrderBy.values()[orderBy])
                .isASC(isASC)
                .build();
        return ResponseEntity.ok(ResPageBoardDto.from(tagService.getTagBoardsById(id, pageBoardDataDto)));
    }

    @PostMapping
    public ResponseEntity<ResTagDto> createTag(@RequestBody ReqCreateTagDto dto) {
        return ResponseEntity.ok(tagService.createTag(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResTagDto> updateTag(@PathVariable Long id,
                                               @RequestBody ReqUpdateTagDto dto) {
        return ResponseEntity.ok(tagService.updateTag(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable Long id) {
        tagService.deleteTag(id);
        return ResponseEntity.noContent().build();
    }


}
