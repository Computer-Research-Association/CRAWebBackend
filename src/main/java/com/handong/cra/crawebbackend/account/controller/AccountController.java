package com.handong.cra.crawebbackend.account.controller;


import com.handong.cra.crawebbackend.account.domain.ManageTokenCategory;
import com.handong.cra.crawebbackend.account.dto.req.ReqFindUsernameDto;
import com.handong.cra.crawebbackend.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/account")
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/valid/username") // 유저 있는지 확인
    public ResponseEntity<Void> validUsername(@RequestParam String username) {
        if (accountService.validUsername(username))// 존재하지 않음.
            return ResponseEntity.ok().build();
        else return ResponseEntity.unprocessableEntity().build(); // 이미 존재함.
    }

    @GetMapping("/find/username")
    public ResponseEntity<String> findUsername(ReqFindUsernameDto reqFindUsernameDto){
        return ResponseEntity.ok(accountService.findUsername(reqFindUsernameDto.getStudentId(), reqFindUsernameDto.getName(), reqFindUsernameDto.getEmail()));
    }

    @PostMapping("/password-change")
    public ResponseEntity<Void> requestChangingPassword(@RequestParam String username) {
        accountService.requestChangingPassword(username);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/valid/email-request")
    public ResponseEntity<Void> emailVerification(@RequestParam String email){
        accountService.emailValidCheck(email);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/valid/email-code")
    public ResponseEntity<Void> emailCodeCheck(@RequestParam String code) {
        accountService.codeValidCheck(code, ManageTokenCategory.EMAIL_VALID);
        return ResponseEntity.ok().build();
    }
}
