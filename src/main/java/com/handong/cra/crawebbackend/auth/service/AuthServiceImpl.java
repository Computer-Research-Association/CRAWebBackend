package com.handong.cra.crawebbackend.auth.service;

import com.handong.cra.crawebbackend.account.domain.ManageTokenCategory;
import com.handong.cra.crawebbackend.account.service.AccountService;
import com.handong.cra.crawebbackend.auth.dto.LoginDto;
import com.handong.cra.crawebbackend.auth.dto.ReissueTokenDto;
import com.handong.cra.crawebbackend.auth.dto.SignupDto;
import com.handong.cra.crawebbackend.auth.dto.TokenDto;
import com.handong.cra.crawebbackend.auth.repository.RefreshTokenRepository;
import com.handong.cra.crawebbackend.exception.account.AccountUserAlreadyExistsException;
import com.handong.cra.crawebbackend.exception.auth.AuthInvalidPasswordException;
import com.handong.cra.crawebbackend.exception.auth.AuthInvalidTokenException;
import com.handong.cra.crawebbackend.exception.auth.AuthLoginFailException;
import com.handong.cra.crawebbackend.exception.auth.AuthTokenExpiredException;
import com.handong.cra.crawebbackend.exception.user.UserDormantUserLoginException;
import com.handong.cra.crawebbackend.user.dto.LoginUserDto;
import com.handong.cra.crawebbackend.user.dto.UserDetailDto;
import com.handong.cra.crawebbackend.user.service.UserService;
import com.handong.cra.crawebbackend.util.AESUtill;
import com.handong.cra.crawebbackend.util.JwtTokenProvider;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final AccountService accountService;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public SignupDto signup(final SignupDto signupDto) {
        String password;
        if (userService.isUserExist(signupDto.getUsername())) { throw new AccountUserAlreadyExistsException(); }
        accountService.codeValidCheck(signupDto.getCode(), ManageTokenCategory.SIGNUP);
        try { password = AESUtill.AESDecrypt(signupDto.getPassword()); }
        catch (Exception e) { throw new AuthInvalidPasswordException(); }

        // 잘못된 패스워드
        if (password.isEmpty()) { throw new AuthInvalidPasswordException(); }
        signupDto.setPassword(passwordEncoder.encode(password));
        return userService.save(signupDto);
    }

    @Override
    @Transactional
    public LoginDto login(final LoginDto loginDto) {
        String password;
        try { password = AESUtill.AESDecrypt(loginDto.getPassword()); }
        catch (Exception e) { throw new AuthInvalidPasswordException(); }

        if (password.isEmpty()) { throw new AuthInvalidPasswordException(); }
        LoginUserDto loginUserDto = userService.getUserIfRegistered(loginDto.getUsername(), loginDto.getPassword());

        if (loginUserDto == null) { throw new AuthLoginFailException(); }
        if (!passwordEncoder.matches(password, loginUserDto.getPassword())) throw new AuthLoginFailException();
        // TODO : token 정보 전달 방식으로 변경
        UserDetailDto userDetailDto = userService.getUserDetailByUsername(loginUserDto.getUsername()); // 로그인시 전달할 유저의 정보

        if (userDetailDto.getDeleted()) { /* 휴면 계정 로그인시 */ throw new UserDormantUserLoginException();}
        userService.setLoginTimeById(userDetailDto.getId()); //로그인 시간 갱신
        refreshTokenRepository.deleteAllByUserId(userDetailDto.getId()); // 이미 존재하면 삭제

        final TokenDto tokenDto = jwtTokenProvider.generateTokenByLogin(loginUserDto.getUsername()); // 새로 생성

        return LoginDto.of(userDetailDto, tokenDto);
    }

    @Override
    public TokenDto reissueToken(final ReissueTokenDto reissueTokenDto) {
        final TokenDto tokenDto = jwtTokenProvider.reissueToken(reissueTokenDto);
        if (tokenDto.getAccessToken().equals("invalid")) { throw new AuthInvalidTokenException(); }
        if (tokenDto.getAccessToken().equals("expired")) { throw new AuthTokenExpiredException(); }
        return tokenDto;
    }

    @Override
    @Transactional
    public void logout(final Long userId) {
        refreshTokenRepository.deleteAllByUserId(userId);
    }
}
