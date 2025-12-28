package com.handong.cra.crawebbackend.user.service;

import com.handong.cra.crawebbackend.account.domain.ManageTokenCategory;
import com.handong.cra.crawebbackend.account.service.AccountService;
import com.handong.cra.crawebbackend.auth.dto.SignupDto;
import com.handong.cra.crawebbackend.exception.auth.AuthForbiddenActionException;
import com.handong.cra.crawebbackend.exception.auth.AuthLoginFailException;
import com.handong.cra.crawebbackend.exception.user.UserInvalidPasswordException;
import com.handong.cra.crawebbackend.file.domain.S3ImageCategory;
import com.handong.cra.crawebbackend.file.service.S3ImageService;
import com.handong.cra.crawebbackend.exception.user.UserNotFoundException;
import com.handong.cra.crawebbackend.user.domain.User;
import com.handong.cra.crawebbackend.user.dto.LoginUserDto;
import com.handong.cra.crawebbackend.user.dto.UpdateUserDto;
import com.handong.cra.crawebbackend.user.dto.UpdateUserPasswordDto;
import com.handong.cra.crawebbackend.user.dto.UserDetailDto;
import com.handong.cra.crawebbackend.user.repository.UserRepository;
import com.handong.cra.crawebbackend.util.AESUtill;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final S3ImageService s3ImageService;
    private final AccountService accountService;
    private final PasswordEncoder passwordEncoder;


    @Override
    @Transactional(readOnly = true)
    public Boolean isUserExist(final String username) {
        return userRepository.findByUsername(username) != null;
    }

    @Override
    @Transactional(readOnly = true)
    public LoginUserDto getUserIfRegistered(final String username, final String password) {
        final User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new AuthLoginFailException();
        }
        return LoginUserDto.from(user);
    }

    @Override
    @Transactional
    public SignupDto save(final SignupDto signupDto) {
        final User user = User.from(signupDto);
        final User savedUser = userRepository.save(user);
        signupDto.setId(savedUser.getId());
        return signupDto;
    }

    @Override
    @Transactional
    public String updateUserProfileImage(final Long userId, final String imgUrl) {
        if (userId == null) {
            throw new AuthForbiddenActionException();
        }
        if (imgUrl == null || !imgUrl.contains("temp/")) { // TODO : exception
            return null;
        }
        final User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        if (user.getImgUrl() != null) { // 기존 이미지 삭제
            s3ImageService.transferImage(user.getImgUrl(), S3ImageCategory.DELETED);
        }
        user.setImgUrl(s3ImageService.transferImage(imgUrl, S3ImageCategory.USER)); // 새로운 이미지 설정
        return user.getImgUrl();
    }

    @Override
    @Transactional
    public UpdateUserDto updateUserInfo(final UpdateUserDto updateUserDto) {
        if (updateUserDto.getId() == null) throw new AuthForbiddenActionException();
        User user = userRepository.findById(updateUserDto.getId()).orElseThrow(UserNotFoundException::new);
        user = user.update(updateUserDto);
        return UpdateUserDto.from(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetailDto getUserDetailByUsername(final String username) {
        final User user = userRepository.findByUsername(username);
        if (user == null) throw new UserNotFoundException();
        return UserDetailDto.from(user);
    }

    @Override
    @Transactional
    public void updateUserPassword(final UpdateUserPasswordDto updateUserPasswordDto) {
        String newPassword;
        try {
            newPassword = AESUtill.AESDecrypt(updateUserPasswordDto.getPassword());
        } catch (Exception e) {
            throw new UserInvalidPasswordException();
        }
        final Long userId = accountService.codeValidCheck(updateUserPasswordDto.getCode(), ManageTokenCategory.PASSWORD_CHANGE);
        final User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        user.setPassword(passwordEncoder.encode(newPassword));
    }


    @Override
    @Transactional
    public Boolean deleteUser(final UpdateUserDto updateUserDto) {
        if (updateUserDto.getId() == null) throw new AuthForbiddenActionException();
        final User user = userRepository.findById(updateUserDto.getId())
                .orElseThrow(UserNotFoundException::new);
        userRepository.delete(user);
        return null;
    }

    @Override
    @Transactional
    public void setLoginTimeById(final Long userId) {
        final User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        user.setLastLoginAt(LocalDateTime.now());
    }
}
