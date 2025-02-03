package com.handong.cra.crawebbackend.account.controller;


import com.handong.cra.crawebbackend.account.domain.ManageTokenCategory;
import com.handong.cra.crawebbackend.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/account")
public class AccountController {

    private final AccountService accountService;

    @PostMapping("valid/username") // 유저 있는지 확인
    public ResponseEntity<Void> validUsername(@RequestParam String username) {
        if (accountService.validUsername(username))// 존재하지 않음.
            return ResponseEntity.ok().build();
        else return ResponseEntity.unprocessableEntity().build(); // 이미 존재함.
    }

    @PostMapping("/password-change")
    public ResponseEntity<?> requestChangingPassword(@RequestParam String username) {
        accountService.requestChangingPassword(username);
        return ResponseEntity.ok().build();
    }
//
//    //test
//    @PostMapping("/valid/code/signup")
//    public ResponseEntity<Void> signupCodeValidCheck(@RequestParam String code) {
//        accountService.codeValidCheck(code, ManageTokenCategory.SIGNUP);
//        return ResponseEntity.ok().build();
//    }
//
//    @PostMapping("/valid/code/password-change")
//    public ResponseEntity<Void> passwordChangeCodeValidCheck(@RequestParam String code) {
//        accountService.codeValidCheck(code, ManageTokenCategory.PASSWORD_CHANGE);
//        return ResponseEntity.ok().build();
//    }
}
