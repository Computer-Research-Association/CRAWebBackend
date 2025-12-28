package com.handong.cra.crawebbackend.user.service;

import com.handong.cra.crawebbackend.auth.dto.SignupDto;
import com.handong.cra.crawebbackend.user.dto.LoginUserDto;
import com.handong.cra.crawebbackend.user.dto.UpdateUserDto;
import com.handong.cra.crawebbackend.user.dto.UpdateUserPasswordDto;
import com.handong.cra.crawebbackend.user.dto.UserDetailDto;

public interface UserService {
    /**
     * username 중복 확인
     *
     * @param username 확인할 username
     */
    Boolean isUserExist(String username);

    /**
     * 유저 로그인 처리
     *
     * @param username username
     * @param password password
     */
    LoginUserDto getUserIfRegistered(String username, String password);

    /**
     * 유저 회원가입
     *
     * @param signupDto 회원가입 정보 DTO
     */
    SignupDto save(SignupDto signupDto);


    /**
     * 유저 프로필 이미지 변경
     *
     * @param userid 변경할 유저 id
     * @param imgUrl 변경할 이미지 url
     */
    String updateUserProfileImage(Long userid, String imgUrl);

    /**
     * 유저 정보 수정
     *
     * @param updateUserDto 변경할 유저 데이터 DTO
     */
    UpdateUserDto updateUserInfo(UpdateUserDto updateUserDto);

    /**
     * 유저 정보 반환
     *
     * @param username 확인할 유저의 username
     */
    UserDetailDto getUserDetailByUsername(String username);

    /**
     * 유저 비밀번호 변경
     *
     * @param updateUserPasswordDto 변경할 유저 및 패스워드 정보 DTO
     */
    void updateUserPassword(UpdateUserPasswordDto updateUserPasswordDto);

    /**
     * 유저 삭제
     *
     * @param updateUserDto 삭제할 유저의 데이터 DTO
     */
    Boolean deleteUser(UpdateUserDto updateUserDto);

    /**
     * 유저 로그인 시간 기록 <br/>
     * - 휴면 계정 기능 활용
     *
     * @param userId 로그인 한 유저의 id
     */
    void setLoginTimeById(Long userId);

}
