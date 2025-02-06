package com.handong.cra.crawebbackend.user.service;

import com.handong.cra.crawebbackend.auth.dto.SignupDto;
import com.handong.cra.crawebbackend.user.dto.LoginUserDto;
import com.handong.cra.crawebbackend.user.dto.UpdateUserDto;
import com.handong.cra.crawebbackend.user.dto.UpdateUserPasswordDto;
import com.handong.cra.crawebbackend.user.dto.UserDetailDto;

public interface UserService {
    public Boolean isUserExist(String username);
    public LoginUserDto getUserIfRegistered(String username, String password);
    public SignupDto save(SignupDto signupDto);
    public String updateUserProfileImage(Long userid ,String imgUrl);
    public UpdateUserDto updateUserInfo(UpdateUserDto updateUserDto);
    public UserDetailDto getUserDetailByUsername(String username);
    public void updateUserPassword(UpdateUserPasswordDto updateUserPasswordDto);
}
