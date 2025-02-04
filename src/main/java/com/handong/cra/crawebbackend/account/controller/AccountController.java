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

    @PostMapping("/valid/username") // 유저 있는지 확인
    public ResponseEntity<Void> validUsername(@RequestParam String username) {
        if (accountService.validUsername(username))// 존재하지 않음.
            return ResponseEntity.ok().build();
        else return ResponseEntity.unprocessableEntity().build(); // 이미 존재함.
    }

    @PostMapping("/find/username")
    public ResponseEntity<String> findUsername(@RequestBody ReqFindUsernameDto reqFindUsernameDto){

        return ResponseEntity.ok(accountService.findUsername(reqFindUsernameDto.getStudentId(), reqFindUsernameDto.getName(), reqFindUsernameDto.getEmail()));
    }

    @PostMapping("/password-change")
    public ResponseEntity<?> requestChangingPassword(@RequestParam String username) {
        accountService.requestChangingPassword(username);
        return ResponseEntity.ok().build();
    }
}
