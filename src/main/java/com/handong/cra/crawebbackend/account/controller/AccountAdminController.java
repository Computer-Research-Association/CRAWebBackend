package com.handong.cra.crawebbackend.account.controller;


import com.handong.cra.crawebbackend.account.dto.response.ResCodeDto;
import com.handong.cra.crawebbackend.account.service.AccountService;
import com.handong.cra.crawebbackend.util.AESUtill;
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

//
//    @PostMapping("/test")
//    public ResponseEntity<Void> test() throws Exception {
//        log.info("test : {} ", AESUtill.AESDecrypt("MXUfexNrZHJgh10kv6ipWA=="));
//        return ResponseEntity.ok().build();
//    }
}
