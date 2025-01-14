package com.handong.cra.crawebbackend.user.service;

import com.handong.cra.crawebbackend.auth.dto.SignupDto;
import com.handong.cra.crawebbackend.user.dto.LoginUserDto;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public interface UserService {
    public Boolean isUserExist(String username);
    public LoginUserDto getUserIfRegistered(String username, String password);
    public SignupDto save(SignupDto signupDto);
    public Collection<? extends GrantedAuthority> getAuthorities(String username);
}
