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

    @PostMapping("/signup")
    public ResponseEntity<ResSignupDto> signup(@RequestBody ReqSignupDto reqSignupDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ResSignupDto.from(authService.signup(SignupDto.from(reqSignupDto))));
    }

    @PostMapping("/login")
    public ResponseEntity<ResLoginDto> login(@RequestBody ReqLoginDto reqLoginDto) {
        return ResponseEntity.ok(ResLoginDto.from(authService.login(LoginDto.from(reqLoginDto))));
    }

    @PostMapping("/reissue-token")
    public ResponseEntity<ResTokenDto> reissueToken(@RequestBody ReqReissueTokenDto reqTokenDto) {
        return ResponseEntity.status(HttpStatus.OK).body(ResTokenDto.from(authService.reissueToken(ReissueTokenDto.from(reqTokenDto))));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        authService.logout(customUserDetails.getUserId());
        return ResponseEntity.ok().build();
    }
}
