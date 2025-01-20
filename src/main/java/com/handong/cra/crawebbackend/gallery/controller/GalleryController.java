package com.handong.cra.crawebbackend.gallery.controller;

import com.handong.cra.crawebbackend.board.domain.BoardOrderBy;
import com.handong.cra.crawebbackend.board.domain.Category;
import com.handong.cra.crawebbackend.board.dto.ListBoardDto;
import com.handong.cra.crawebbackend.board.dto.response.ResListBoardDto;
import com.handong.cra.crawebbackend.exception.gallery.GalleryPageSizeLimitExceededException;
import com.handong.cra.crawebbackend.gallery.dto.GalleryDto;
import com.handong.cra.crawebbackend.gallery.dto.response.ResGalleryDto;
import com.handong.cra.crawebbackend.gallery.service.GalleryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/gallery")
public class GalleryController {

    @Value("${spring.data.page.MAX_PER_PAGE}")
    private Integer MAX_PAGE_SIZE;

    private final GalleryService galleryService;


    @GetMapping("/page/{page}")
    public ResponseEntity<List<ResGalleryDto>> getPageGallery(
            @PathVariable Long page,
            @RequestParam(required = false, defaultValue = "0") Integer perPage,
            @RequestParam(required = false, defaultValue = "0") Integer orderBy,
            @RequestParam(required = false, defaultValue = "true") Boolean isASC
    ){
        if (perPage > MAX_PAGE_SIZE)
            throw new GalleryPageSizeLimitExceededException();

        List<GalleryDto> galleryDtos = galleryService.getPaginationGallery(page, perPage, BoardOrderBy.values()[orderBy], isASC);
        return ResponseEntity.ok(galleryDtos.stream().map(ResGalleryDto::from).toList());

    }

    @GetMapping("/{id}")
    public ResponseEntity<ResGalleryDto> getGalleryById(@PathVariable Long id){
        return ResponseEntity.ok(ResGalleryDto.from(galleryService.getDetailGalleryById(id)));
    }
}
