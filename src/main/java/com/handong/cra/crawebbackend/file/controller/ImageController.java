package com.handong.cra.crawebbackend.file.controller;

import com.handong.cra.crawebbackend.file.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/")
@CrossOrigin(origins = "http://localhost:3000")
public class ImageController {

    private final ImageService imageService;


//    @PostMapping("/test")
//    public ResponseEntity<String> test(@RequestParam MultipartFile image){
//        return ResponseEntity.ok(imageService.uploadImage(image));
//    }
//
//






}
