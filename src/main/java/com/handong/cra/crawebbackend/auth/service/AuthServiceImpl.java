package com.handong.cra.crawebbackend.auth.service;

import com.handong.cra.crawebbackend.account.domain.ManageTokenCategory;
import com.handong.cra.crawebbackend.account.service.AccountService;
import com.handong.cra.crawebbackend.auth.dto.LoginDto;
import com.handong.cra.crawebbackend.auth.dto.ReissueTokenDto;
import com.handong.cra.crawebbackend.auth.dto.SignupDto;
import com.handong.cra.crawebbackend.auth.dto.TokenDto;
import com.handong.cra.crawebbackend.auth.dto.response.ResTokenDto;
import com.handong.cra.crawebbackend.user.dto.LoginUserDto;
import com.handong.cra.crawebbackend.user.dto.UserDetailDto;
import com.handong.cra.crawebbackend.user.repository.UserRepository;
import com.handong.cra.crawebbackend.user.service.UserService;
import com.handong.cra.crawebbackend.util.AESUtill;
import com.handong.cra.crawebbackend.util.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final AccountService accountService;

    @Override
    public SignupDto signup(SignupDto signupDto) {
        if (userService.isUserExist(signupDto.getUsername())) {
            return null;
        }
        accountService.codeValidCheck(signupDto.getCode(), ManageTokenCategory.SIGNUP);

        signupDto.setPassword(passwordEncoder.encode(signupDto.getPassword()));

        return userService.save(signupDto);
    }

    @Override
    public LoginDto login(LoginDto loginDto) {
        String password = "";
        try {
            password = AESUtill.AESDecrypt(loginDto.getPassword());
        } catch (Exception e) {

        }
        if (password.isEmpty()) {
            // 잘못된 패스워드
        }

        LoginUserDto loginUserDto = userService.getUserIfRegistered(loginDto.getUsername(), loginDto.getPassword());
        if (loginUserDto == null) {
            throw new BadCredentialsException("Invalid username or password");
        }

        if (!passwordEncoder.matches(password, loginUserDto.getPassword())) {
            throw new BadCredentialsException("Invalid username or password");
        }

        TokenDto tokenDto = jwtTokenProvider.generateTokenByLogin(loginUserDto.getUsername());
        UserDetailDto userDetailDto = userService.getUserDetailByUsername(loginUserDto.getUsername());

        return LoginDto.of(userDetailDto, tokenDto);
    }

    @Override
    public TokenDto reissueToken(ReissueTokenDto reissueTokenDto) {
        return jwtTokenProvider.reissueToken(reissueTokenDto);
    }


}
