package com.handong.cra.crawebbackend.auth.service;

import com.handong.cra.crawebbackend.account.domain.ManageTokenCategory;
import com.handong.cra.crawebbackend.account.service.AccountService;
import com.handong.cra.crawebbackend.auth.domain.RefreshToken;
import com.handong.cra.crawebbackend.auth.dto.LoginDto;
import com.handong.cra.crawebbackend.auth.dto.ReissueTokenDto;
import com.handong.cra.crawebbackend.auth.dto.SignupDto;
import com.handong.cra.crawebbackend.auth.dto.TokenDto;
import com.handong.cra.crawebbackend.auth.dto.response.ResTokenDto;
import com.handong.cra.crawebbackend.auth.repository.RefreshTokenRepository;
import com.handong.cra.crawebbackend.exception.account.AccountUserAlreadyExistsException;
import com.handong.cra.crawebbackend.exception.auth.AuthInvalidPasswordException;
import com.handong.cra.crawebbackend.exception.auth.AuthInvalidTokenException;
import com.handong.cra.crawebbackend.exception.auth.AuthLoginFailException;
import com.handong.cra.crawebbackend.exception.auth.AuthTokenExpiredException;
import com.handong.cra.crawebbackend.exception.user.UserDormantUserLoginException;
import com.handong.cra.crawebbackend.user.dto.LoginUserDto;
import com.handong.cra.crawebbackend.user.dto.UserDetailDto;
import com.handong.cra.crawebbackend.user.repository.UserRepository;
import com.handong.cra.crawebbackend.user.service.UserService;
import com.handong.cra.crawebbackend.util.AESUtill;
import com.handong.cra.crawebbackend.util.JwtTokenProvider;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;

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
    public SignupDto signup(SignupDto signupDto) {
        if (userService.isUserExist(signupDto.getUsername())) throw new AccountUserAlreadyExistsException();

        accountService.codeValidCheck(signupDto.getCode(), ManageTokenCategory.SIGNUP);
        String password = "";
        try {
            password = AESUtill.AESDecrypt(signupDto.getPassword());
        } catch (Exception e) {
            throw new AuthInvalidPasswordException();
        }

        // 잘못된 패스워드
        if (password.isEmpty()) throw new AuthInvalidPasswordException();

        signupDto.setPassword(passwordEncoder.encode(password));

        return userService.save(signupDto);
    }

    @Override
    @Transactional
    public LoginDto login(LoginDto loginDto) {
        String password = "";
        try {
            password = AESUtill.AESDecrypt(loginDto.getPassword());
        } catch (Exception e) {
            throw new AuthInvalidPasswordException();
        }
        if (password.isEmpty()) throw new AuthInvalidPasswordException();


        LoginUserDto loginUserDto = userService.getUserIfRegistered(loginDto.getUsername(), loginDto.getPassword());

        if (loginUserDto == null) throw new AuthLoginFailException();

        if (!passwordEncoder.matches(password, loginUserDto.getPassword())) throw new AuthLoginFailException();


        // 로그인시 전달할 유저의 정보
        UserDetailDto userDetailDto = userService.getUserDetailByUsername(loginUserDto.getUsername());

        // 휴면 계정 로그인시
        if (userDetailDto.getDeleted()) throw new UserDormantUserLoginException();

        //로그인 시간 갱신
        userService.setLoginTimeById(userDetailDto.getId());

        // 이미 존재하면 삭제
        refreshTokenRepository.deleteAllByUserId(userDetailDto.getId());

        // 새로 생성
        final TokenDto tokenDto = jwtTokenProvider.generateTokenByLogin(loginUserDto.getUsername());
        return LoginDto.of(userDetailDto, tokenDto);
    }

    @Override
    public TokenDto reissueToken(ReissueTokenDto reissueTokenDto) {
        TokenDto tokenDto = jwtTokenProvider.reissueToken(reissueTokenDto);
        log.info(reissueTokenDto.getRefreshToken());
        if (tokenDto.getAccessToken().equals("invalid")) throw new AuthInvalidTokenException();
        else if (tokenDto.getAccessToken().equals("expired")) throw new AuthTokenExpiredException();
        else return tokenDto;
    }

    @Override
    @Transactional
    public void logout(Long userId) {
        refreshTokenRepository.deleteAllByUserId(userId);
    }
}
