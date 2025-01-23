package com.handong.cra.crawebbackend.account.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/account")
public class AccountController {

    @GetMapping("/change-password")
    public ResponseEntity<?> requestChangingPassword(@RequestParam Short length){
        return ResponseEntity.ok().build();
    }
}
