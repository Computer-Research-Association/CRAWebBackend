package com.handong.cra.crawebbackend.auth.service;

import com.handong.cra.crawebbackend.auth.dto.LoginDto;
import com.handong.cra.crawebbackend.auth.dto.ReissueTokenDto;
import com.handong.cra.crawebbackend.auth.dto.SignupDto;
import com.handong.cra.crawebbackend.auth.dto.response.ResTokenDto;

public interface AuthService {
    public SignupDto signup(SignupDto signupDto);

    public ResTokenDto login(LoginDto loginDto);
    public ResTokenDto reissueToken(ReissueTokenDto reissueTokenDto);
}
