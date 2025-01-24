package com.handong.cra.crawebbackend.user.controller;


import com.handong.cra.crawebbackend.project.dto.UpdateProjectDto;
import com.handong.cra.crawebbackend.user.dto.UpdateUserDto;
import com.handong.cra.crawebbackend.user.dto.UpdateUserPasswordDto;
import com.handong.cra.crawebbackend.user.dto.request.ReqUpdateUserDto;
import com.handong.cra.crawebbackend.user.dto.request.ReqUpdateUserPasswordDto;
import com.handong.cra.crawebbackend.user.dto.response.ResUpdateUserDto;
import com.handong.cra.crawebbackend.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/user")
public class UserController {

    private final UserService userService;

    @PutMapping("/{id}")
    public ResponseEntity<ResUpdateUserDto> updateUserProfileImage(@PathVariable Long id, @RequestBody ReqUpdateUserDto reqUpdateUserDto) {
        return ResponseEntity.ok(ResUpdateUserDto.from((userService.updateUserInfo(id, UpdateUserDto.from(reqUpdateUserDto)))));
    }


    @PutMapping("/password-change")
    public ResponseEntity<Void> updatePassword(@RequestParam ReqUpdateUserPasswordDto reqUpdateUserPasswordDto) {
         userService.updateUserPassword(UpdateUserPasswordDto.from(reqUpdateUserPasswordDto));
         return ResponseEntity.ok().build();

    }

}
