package com.handong.cra.crawebbackend.account.controller;

import com.handong.cra.crawebbackend.account.domain.ManageTokenCategory;
import com.handong.cra.crawebbackend.account.dto.FindUsernameDto;
import com.handong.cra.crawebbackend.account.dto.req.ReqFindUsernameDto;
import com.handong.cra.crawebbackend.account.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/account")
public class AccountController {
    private final AccountService accountService;

    @GetMapping("/valid/username") // 유저 있는지 확인
    @Operation(summary = "유저 아이디 중복 확인", description = "회원가입시 아이디 중복 확인")
    public ResponseEntity<Void> validUsername(@RequestParam final String username) {
        if (accountService.validUsername(username)) { // 존재하지 않음.
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.unprocessableEntity().build(); // 이미 존재함.
    }

    @PostMapping("/valid/email-request") // 이메일 확인용 전송
    @Operation(summary = "이메일 인증 요청", description = "이메일 인증 메일 전송. 중복시 에러 발생")
    public ResponseEntity<Void> emailVerification(@RequestParam final String email) {
        accountService.emailValidCheck(email);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/valid/email-code") // 이메일 검증
    @Operation(summary = "이메일 인증", description = "이메일 인증코드로 이메일 인증")
    public ResponseEntity<Void> emailCodeCheck(@RequestParam final String code) {
        accountService.codeValidCheck(code, ManageTokenCategory.EMAIL_VALID);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/find/username") // 유저 찾기
    @Operation(summary = "아이디 찾기")
    public ResponseEntity<String> findUsername(final ReqFindUsernameDto reqFindUsernameDto) {
        return ResponseEntity.ok(accountService.findUsername(FindUsernameDto.from(reqFindUsernameDto)));
    }

    @PostMapping("/password-change") // 패스워드 변경
    @Operation(summary = "패스워드 변경 이메일 전송", description = "유저에 저장되어있는 이메일로 패스워드 변경 이메일 전송")
    public ResponseEntity<Void> requestChangingPassword(@RequestParam final String username) {
        accountService.requestChangingPassword(username);
        return ResponseEntity.ok().build();
    }
}
