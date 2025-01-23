package com.handong.cra.crawebbackend.account.controller;


import com.handong.cra.crawebbackend.account.dto.CodeDto;
import com.handong.cra.crawebbackend.account.dto.response.ResCodeDto;
import com.handong.cra.crawebbackend.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin/account")
public class AccountAdminController {

    private final AccountService accountService;

    @GetMapping("/code/signup")
    public ResponseEntity<List<ResCodeDto>> generateSignupCodes(@RequestParam Short length){
        return ResponseEntity.ok(accountService.generateSignupCodes(length).stream().map(ResCodeDto::from).toList());
    }
}
