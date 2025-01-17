package com.handong.cra.crawebbackend.user.service;

import com.handong.cra.crawebbackend.auth.dto.SignupDto;
import com.handong.cra.crawebbackend.file.domain.S3ImageCategory;
import com.handong.cra.crawebbackend.file.service.S3ImageService;
import com.handong.cra.crawebbackend.user.domain.User;
import com.handong.cra.crawebbackend.user.dto.LoginUserDto;
import com.handong.cra.crawebbackend.user.dto.UpdateUserDto;
import com.handong.cra.crawebbackend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final S3ImageService s3ImageService;


    @Override
    public Boolean isUserExist(String username) {
        return userRepository.findByUsername(username) != null;
    }

    @Override
    public LoginUserDto getUserIfRegistered(String username, String password) {
        log.info("pass: {}", password);
        User user = userRepository.findByUsername(username);

        return LoginUserDto.from(user);
    }

    @Override
    public SignupDto save(SignupDto signupDto) {
        User user = User.from(signupDto);
        userRepository.save(user);
        signupDto.setId(user.getId());
        return signupDto;
    }

    @Override
    public UpdateUserDto updateUserInfo(Long id, UpdateUserDto updateUserDto) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("no user"));

        //img logic
        if (updateUserDto.getImgUrl().contains("temp/")) {
            if (user.getImgUrl() != null)
                s3ImageService.transferImage(user.getImgUrl(),S3ImageCategory.DELETED);
            updateUserDto.setImgUrl(s3ImageService.transferImage(updateUserDto.getImgUrl(), S3ImageCategory.USER));
        }
        user = user.update(updateUserDto);

        return UpdateUserDto.from(user);
    }
}
