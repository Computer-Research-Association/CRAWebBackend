package com.handong.cra.crawebbackend.auth.service;

import com.handong.cra.crawebbackend.auth.dto.LoginDto;
import com.handong.cra.crawebbackend.auth.dto.ReissueTokenDto;
import com.handong.cra.crawebbackend.auth.dto.SignupDto;
import com.handong.cra.crawebbackend.auth.dto.TokenDto;
import com.handong.cra.crawebbackend.auth.dto.response.ResTokenDto;

public interface AuthService {
    /**
     * 유저 가입
     *
     * @param signupDto 가입 정보 DTO
     */
    SignupDto signup(SignupDto signupDto);

    /**
     * 유저 로그인 <br />
     * 리프레시 토큰 발급, DB 저장
     *
     * @param loginDto 로그인 정보 DTO
     */
    LoginDto login(LoginDto loginDto);

    /**
     * 엑세스 토큰 발급
     *
     * @param reissueTokenDto 토큰 DTO
     */
    TokenDto reissueToken(ReissueTokenDto reissueTokenDto);

    /**
     * 로그아웃 <br />
     * DB 리프레시 토큰 제거
     *
     * @param userId 유저 id
     */
    void logout(Long userId);
}
