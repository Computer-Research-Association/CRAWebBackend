package com.handong.cra.crawebbackend.common.domain.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
public class CommonController {

    @GetMapping("")
    public ResponseEntity<Void> root(){
        return ResponseEntity.ok().build();
    }

}
