package com.handong.cra.crawebbackend.auth.controller;

import com.handong.cra.crawebbackend.auth.domain.CustomUserDetails;
import com.handong.cra.crawebbackend.auth.dto.LoginDto;
import com.handong.cra.crawebbackend.auth.dto.ReissueTokenDto;
import com.handong.cra.crawebbackend.auth.dto.SignupDto;
import com.handong.cra.crawebbackend.auth.dto.request.ReqLoginDto;
import com.handong.cra.crawebbackend.auth.dto.request.ReqReissueTokenDto;
import com.handong.cra.crawebbackend.auth.dto.request.ReqSignupDto;
import com.handong.cra.crawebbackend.auth.dto.response.ResLoginDto;
import com.handong.cra.crawebbackend.auth.dto.response.ResSignupDto;
import com.handong.cra.crawebbackend.auth.dto.response.ResTokenDto;
import com.handong.cra.crawebbackend.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup") // 가입
    @Operation(summary = "유저 가입", description = "새로운 유저 가입")
    public ResponseEntity<ResSignupDto> signup(@RequestBody final ReqSignupDto reqSignupDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ResSignupDto.from(authService.signup(SignupDto.from(reqSignupDto))));
    }

    @PostMapping("/login") // 로그인
    @Operation(summary = "유저 로그인", description = "패스워드 암호화 필요, 리프레시 토큰 발급")
    public ResponseEntity<ResLoginDto> login(@RequestBody final ReqLoginDto reqLoginDto) {
        return ResponseEntity.ok(ResLoginDto.from(authService.login(LoginDto.from(reqLoginDto))));
    }

    @PostMapping("/reissue-token") // access token 발급
    @Operation(summary = "엑세스 토큰 발급", description = "리프레시 토큰으로 엑세스 토큰 발급")
    public ResponseEntity<ResTokenDto> reissueToken(@RequestBody final ReqReissueTokenDto reqTokenDto) {
        return ResponseEntity.status(HttpStatus.OK).body(ResTokenDto.from(authService.reissueToken(ReissueTokenDto.from(reqTokenDto))));
    }

    @PostMapping("/logout") // 로그아웃
    @Operation(summary = "유저 로그아웃", description = "리프레시 토큰 비활성화")
    public ResponseEntity<Void> logout(@AuthenticationPrincipal final CustomUserDetails customUserDetails) {
        authService.logout(customUserDetails.getUserId());
        return ResponseEntity.ok().build();
    }
}
