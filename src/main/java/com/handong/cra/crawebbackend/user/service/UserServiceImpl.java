package com.handong.cra.crawebbackend.user.service;

import com.handong.cra.crawebbackend.account.domain.ManageTokenCategory;
import com.handong.cra.crawebbackend.account.service.AccountService;
import com.handong.cra.crawebbackend.auth.dto.SignupDto;
import com.handong.cra.crawebbackend.exception.auth.AuthForbiddenActionException;
import com.handong.cra.crawebbackend.exception.user.UserInvalidPasswordException;
import com.handong.cra.crawebbackend.file.domain.S3ImageCategory;
import com.handong.cra.crawebbackend.file.service.S3ImageService;
import com.handong.cra.crawebbackend.exception.user.UserNotFoundException;
import com.handong.cra.crawebbackend.user.domain.User;
import com.handong.cra.crawebbackend.user.domain.UserRoleEnum;
import com.handong.cra.crawebbackend.user.dto.LoginUserDto;
import com.handong.cra.crawebbackend.user.dto.UpdateUserDto;
import com.handong.cra.crawebbackend.user.dto.UpdateUserPasswordDto;
import com.handong.cra.crawebbackend.user.dto.UserDetailDto;
import com.handong.cra.crawebbackend.user.repository.UserRepository;
import com.handong.cra.crawebbackend.util.AESUtill;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final S3ImageService s3ImageService;
    private final AccountService accountService;
    private final PasswordEncoder passwordEncoder;


    @Override
    public Boolean isUserExist(String username) {
        return userRepository.findByUsername(username) != null;
    }

    @Override
    public LoginUserDto getUserIfRegistered(String username, String password) {
        User user = userRepository.findByUsername(username);

        if (user == null) throw new UserNotFoundException();

        return LoginUserDto.from(user);
    }

    @Override
    @Transactional
    public SignupDto save(SignupDto signupDto) {
        User user = User.from(signupDto);
        userRepository.save(user);
        signupDto.setId(user.getId());
        return signupDto;
    }

    @Override
    @Transactional
    public String updateUserProfileImage(Long userId, String imgUrl) {

        if (userId == null) throw new AuthForbiddenActionException();

        if (imgUrl == null || !imgUrl.contains("temp/")) return null;

        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        // 기존 이미지 삭제
        if (user.getImgUrl() != null) s3ImageService.transferImage(user.getImgUrl(), S3ImageCategory.DELETED);

        user.setImgUrl(s3ImageService.transferImage(imgUrl, S3ImageCategory.USER));
        return user.getImgUrl();
    }

    @Override
    public UpdateUserDto updateUserInfo(UpdateUserDto updateUserDto) {
        User user = userRepository.findById(updateUserDto.getUserId()).orElseThrow(UserNotFoundException::new);

        // 권한 없음
        if (!Objects.equals(user.getId(), updateUserDto.getUserId())) throw new AuthForbiddenActionException();

        user = user.update(updateUserDto);


        return UpdateUserDto.from(user);
    }

    @Override
    public UserDetailDto getUserDetailByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) throw new UserNotFoundException();

        return UserDetailDto.from(user);
    }

    @Override
    @Transactional
    public void updateUserPassword(UpdateUserPasswordDto updateUserPasswordDto) {
        // code valid check - > 문제 있으면 throw
        String newPassword = "";
        try {
            newPassword = AESUtill.AESDecrypt(updateUserPasswordDto.getPassword());
        } catch (Exception e) {
            throw new UserInvalidPasswordException();
        }

        Long userId = accountService.codeValidCheck(updateUserPasswordDto.getCode(), ManageTokenCategory.PASSWORD_CHANGE);

        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        user.setPassword(passwordEncoder.encode(newPassword));
    }


    @Override
    public Boolean deleteUser(UpdateUserDto updateUserDto) {
        User user = userRepository.findById(updateUserDto.getId()).orElseThrow(UserNotFoundException::new);


        user.delete();
        return null;
    }
}
