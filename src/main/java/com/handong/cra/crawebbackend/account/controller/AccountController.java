package com.handong.cra.crawebbackend.account.controller;


import com.handong.cra.crawebbackend.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/account")
public class AccountController {

    private final AccountService accountService;
    @GetMapping("/change-password")
    public ResponseEntity<?> requestChangingPassword(@RequestParam Short length){
        return ResponseEntity.ok().build();
    }


    @PostMapping("/valid/signup")
    public ResponseEntity<Void> signupCodeValidCheck(@RequestParam String code) {
        accountService.signupCodeValidCheck(code);
        return ResponseEntity.ok().build();
    }
}
