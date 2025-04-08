package com.handong.cra.crawebbackend.file.controller;

import com.handong.cra.crawebbackend.file.domain.S3ImageCategory;
import com.handong.cra.crawebbackend.file.service.S3ImageService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/image")
public class ImageController {
    private final S3ImageService s3ImageService;

    @PostMapping("/upload")
    @Operation(summary = "이미지 업로드", description = "임시 데이터 위치로 이미지 업로드")
    public ResponseEntity<String> uploadImage(@RequestParam MultipartFile image) {
        return ResponseEntity.ok(s3ImageService.uploadImage(image));
    }
}
