package com.handong.cra.crawebbackend.user.service;

import com.handong.cra.crawebbackend.auth.dto.SignupDto;
import com.handong.cra.crawebbackend.exception.user.UserNotFoundException;
import com.handong.cra.crawebbackend.user.domain.User;
import com.handong.cra.crawebbackend.user.dto.LoginUserDto;
import com.handong.cra.crawebbackend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public Boolean isUserExist(String username) {
        return userRepository.findByUsername(username) != null;
    }

    @Override
    public LoginUserDto getUserIfRegistered(String username, String password) {
        log.info("pass: {}", password);
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UserNotFoundException();
        }

        return LoginUserDto.from(user);
    }

    @Override
    public SignupDto save(SignupDto signupDto) {
        User user = User.from(signupDto);
        userRepository.save(user);
        signupDto.setId(user.getId());
        return signupDto;
    }
}
