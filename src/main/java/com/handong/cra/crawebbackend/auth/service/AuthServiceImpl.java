package com.handong.cra.crawebbackend.auth.service;

import com.handong.cra.crawebbackend.auth.dto.LoginDto;
import com.handong.cra.crawebbackend.auth.dto.ReissueTokenDto;
import com.handong.cra.crawebbackend.auth.dto.SignupDto;
import com.handong.cra.crawebbackend.auth.dto.response.ResTokenDto;
import com.handong.cra.crawebbackend.user.dto.LoginUserDto;
import com.handong.cra.crawebbackend.user.service.UserService;
import com.handong.cra.crawebbackend.util.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Override
    public SignupDto signup(SignupDto signupDto) {
        if (userService.isUserExist(signupDto.getUsername())) {
            return null;
        }

        signupDto.setPassword(passwordEncoder.encode(signupDto.getPassword()));

        return userService.save(signupDto);
    }

    @Override
    public ResTokenDto login(LoginDto loginDto) {
        LoginUserDto loginUserDto = userService.getUserIfRegistered(loginDto.getUsername(), loginDto.getPassword());
        if (loginUserDto == null) {
            throw new BadCredentialsException("Invalid username or password");
        }

        if (!passwordEncoder.matches(loginDto.getPassword(), loginUserDto.getPassword())) {
            throw new BadCredentialsException("Invalid username or password");
        }

        return jwtTokenProvider.generateTokenByLogin(loginUserDto.getUsername());
    }

    @Override
    public ResTokenDto reissueToken(ReissueTokenDto reissueTokenDto) {
        return jwtTokenProvider.reissueToken(reissueTokenDto);
    }


}
