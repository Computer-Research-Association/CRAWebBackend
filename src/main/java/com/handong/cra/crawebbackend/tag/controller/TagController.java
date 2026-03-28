package com.handong.cra.crawebbackend.tag.controller;

import com.handong.cra.crawebbackend.tag.dto.request.ReqCreateTagDto;
import com.handong.cra.crawebbackend.tag.dto.request.ReqUpdateTagDto;
import com.handong.cra.crawebbackend.tag.dto.response.ResTagDto;
import com.handong.cra.crawebbackend.tag.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @GetMapping
    public ResponseEntity<List<ResTagDto>> getAllTags() {
        return ResponseEntity.ok(tagService.getAllTags());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResTagDto> getTagById(@PathVariable Long id) {
        return ResponseEntity.ok(tagService.getTagById(id));
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