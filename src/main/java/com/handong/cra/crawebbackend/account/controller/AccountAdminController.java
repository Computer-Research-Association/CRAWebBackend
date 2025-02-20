package com.handong.cra.crawebbackend.account.controller;


import com.handong.cra.crawebbackend.account.dto.response.ResCodeDto;
import com.handong.cra.crawebbackend.account.service.AccountService;
import com.handong.cra.crawebbackend.user.domain.UserRoleEnum;
import com.handong.cra.crawebbackend.user.dto.response.ResUserDetailDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/api/admin/account")
public class AccountAdminController {

    private final AccountService accountService;

    @GetMapping("/code/signup") // 가입 코드 생성
    public ResponseEntity<List<ResCodeDto>> generateSignupCodes(@RequestParam Short length) {
        return ResponseEntity.ok(accountService.generateSignupCodes(length).stream().map(ResCodeDto::from).toList());
    }

    @GetMapping("/users")
    public ResponseEntity<List<ResUserDetailDto>> getUsersByEntranceYear(@RequestParam(required = false, defaultValue = "") String year, @RequestParam(required = false, defaultValue = "") String term) {
        if (year.isEmpty() && term.isEmpty()) throw new RuntimeException("test");
        return ResponseEntity.ok(accountService.getUsersByEntranceYear(year, term).stream().map(ResUserDetailDto::from).toList());
    }

    @PutMapping("/users/{userId}")
    public ResponseEntity<Void> updateUserAuthById(@PathVariable Long userId, @RequestParam Integer authOption) {
        accountService.updateUserAuthById(userId, UserRoleEnum.values()[authOption]);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long userId){

         // 구현중
        return ResponseEntity.ok().build();
    }
}
