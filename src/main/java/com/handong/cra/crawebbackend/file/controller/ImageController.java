package com.handong.cra.crawebbackend.file.controller;

import com.handong.cra.crawebbackend.file.domain.S3ImageCategory;
import com.handong.cra.crawebbackend.file.service.S3ImageService;
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
    public ResponseEntity<String> uploadImage(@RequestParam MultipartFile image) {
        return ResponseEntity.ok(s3ImageService.uploadImage(image));
    }


    // testing
//    @PutMapping("/move")
//    public ResponseEntity<Void> moveImage(@RequestParam String url, @RequestParam Integer imageCategory) {
//        if (s3ImageService.transferImage(url,S3ImageCategory.values()[imageCategory])) return ResponseEntity.ok().build();
//        else return ResponseEntity.internalServerError().build();
//    }
}
