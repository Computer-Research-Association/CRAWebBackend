package com.handong.cra.crawebbackend.gallery.controller;

import com.handong.cra.crawebbackend.gallery.dto.GalleryDto;
import com.handong.cra.crawebbackend.gallery.dto.request.ReqGalleryDto;
import com.handong.cra.crawebbackend.gallery.dto.response.ResGalleryDto;
import com.handong.cra.crawebbackend.gallery.service.GalleryService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/gallery")
public class GalleryAdminController {
    private final GalleryService galleryService;


    @PostMapping("")
    public ResponseEntity<ResGalleryDto> createGallery(@RequestBody ReqGalleryDto reqGalleryDto) {
        return ResponseEntity.ok(ResGalleryDto.from(galleryService.createGallery(GalleryDto.from(reqGalleryDto))));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResGalleryDto> updateGallery(@PathVariable Long id, @RequestBody ReqGalleryDto reqGalleryDto) {
        return ResponseEntity.ok(ResGalleryDto.from(galleryService.updateGallery(id, GalleryDto.from(reqGalleryDto))));
    }

    @GetMapping("")
    public ResponseEntity<List<ResGalleryDto>> getListGallery() {
        return ResponseEntity.ok(galleryService.getGalleryList().stream().map(ResGalleryDto::from).toList());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGalleryById(@PathVariable Long id) {
        galleryService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}