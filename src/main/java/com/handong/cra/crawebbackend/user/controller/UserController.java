package com.handong.cra.crawebbackend.user.controller;


import com.handong.cra.crawebbackend.auth.domain.CustomUserDetails;
import com.handong.cra.crawebbackend.user.dto.UpdateUserDto;
import com.handong.cra.crawebbackend.user.dto.UpdateUserPasswordDto;
import com.handong.cra.crawebbackend.user.dto.request.ReqUpdateUserDto;
import com.handong.cra.crawebbackend.user.dto.request.ReqUpdateUserPasswordDto;
import com.handong.cra.crawebbackend.user.dto.response.ResUpdateUserDto;
import com.handong.cra.crawebbackend.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @PutMapping("/image") // 유저 프로필 이미지 변경
    public ResponseEntity<String> updateUserProfileImage(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestParam String imgUrl) {
        return ResponseEntity.ok(userService.updateUserProfileImage(customUserDetails.getUserId(), imgUrl));
    }

    @PutMapping("/info") // 유저 정보 수정
    public ResponseEntity<ResUpdateUserDto> updateUserInfo(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody ReqUpdateUserDto reqUpdateUserDto) {
        return ResponseEntity.ok(ResUpdateUserDto.from((userService.updateUserInfo(UpdateUserDto.of(customUserDetails.getUserId(), reqUpdateUserDto)))));
    }


    @PutMapping("/password-change") // 유저 비밀번호 변경
    public ResponseEntity<Void> updatePassword(@RequestBody ReqUpdateUserPasswordDto reqUpdateUserPasswordDto) {
        userService.updateUserPassword(UpdateUserPasswordDto.from(reqUpdateUserPasswordDto));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("") // 유저 삭제 (본인 계정)
    public ResponseEntity<Void> deleteUser(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        userService.deleteUser(UpdateUserDto.of(customUserDetails.getUserId(), true));
        return ResponseEntity.ok().build();
    }
}
