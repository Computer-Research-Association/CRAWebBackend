package com.handong.cra.crawebbackend.user.service;

import com.handong.cra.crawebbackend.auth.dto.SignupDto;
import com.handong.cra.crawebbackend.user.dto.LoginUserDto;

public interface UserService {
    public Boolean isUserExist(String username);
    public LoginUserDto getUserIfRegistered(String username, String password);
    public SignupDto save(SignupDto signupDto);
}
