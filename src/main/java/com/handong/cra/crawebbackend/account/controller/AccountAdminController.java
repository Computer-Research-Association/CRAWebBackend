package com.handong.cra.crawebbackend.account.controller;


import com.handong.cra.crawebbackend.account.dto.response.ResCodeDto;
import com.handong.cra.crawebbackend.account.service.AccountService;
import com.handong.cra.crawebbackend.exception.board.PageSizeLimitExceededException;
import com.handong.cra.crawebbackend.user.domain.UserRoleEnum;
import com.handong.cra.crawebbackend.user.dto.PageUserDataDto;
import com.handong.cra.crawebbackend.user.dto.response.ResPageUserDto;
import com.handong.cra.crawebbackend.user.dto.response.ResUserAdminDetailDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/api/admin/account")
public class AccountAdminController {
    private final AccountService accountService;

    @Value("${spring.data.page.MAX_PER_PAGE}")
    private Integer MAX_PAGE_SIZE;

    @GetMapping("/code/signup") // 가입 코드 생성
    @Operation(summary = "가입 코드 생성", description = "가입시 필요한 코드 생성. 파라미터로 갯수 조절 가능")
    public ResponseEntity<List<ResCodeDto>> generateSignupCodes(@RequestParam final Short length) {
        return ResponseEntity.ok(accountService
                .generateSignupCodes(length)
                .stream().map(ResCodeDto::from).toList());
    }

    @GetMapping("/users/page/{page}") // 유저 리스트
    @Operation(summary = "유저 리스트 조회", description = "유저 관리시 유저 리스트로 조회")
    public ResponseEntity<ResPageUserDto> getPaginationUser(
            @PathVariable final Long page, // 0부터 시작
            @RequestParam(required = false, defaultValue = "0") final Integer perPage
    ) {
        if (perPage > MAX_PAGE_SIZE) {
            throw new PageSizeLimitExceededException();
        }
        return ResponseEntity.ok(ResPageUserDto
                .from(accountService
                        .getPaginationUser(PageUserDataDto.builder().page(page)
                                .perPage(perPage).build())));
    }

    @GetMapping("/users/search") // 유저 검색
    @Operation(summary = "유저 검색", description = "이름으로 유저 검색")
    public ResponseEntity<List<ResUserAdminDetailDto>> findUsersByName(
            @RequestParam(required = false, defaultValue = "") final String name) {
        return ResponseEntity.ok(accountService
                .findUsersByName(name).stream()
                .map(ResUserAdminDetailDto::from).toList());
    }

    @PutMapping("/users/auth") // 유저 권한 추가
    @Operation(summary = "유저 권한 추가")
    public ResponseEntity<Void> addUserAuthById(
            @RequestParam final Long userId,
            @RequestParam final Integer authOption) {
        accountService.addUserAuthById(userId, UserRoleEnum.values()[authOption]);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/users/auth") // 유저 권한 삭제
    @Operation(summary = "유저 권한 삭제")
    public ResponseEntity<Void> removeUserAuthById(
            @RequestParam final Long userId,
            @RequestParam final Integer authOption) {
        accountService.removeUserAuthById(userId, UserRoleEnum.values()[authOption]);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/users/active") // 휴면 계정 활성화
    @Operation(summary = "휴면 계정 활성화")
    public ResponseEntity<Void> activeAccount(@RequestParam final Long userId) {
        accountService.activeAccount(userId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/users/{userId}") // 유저 삭제
    @Operation(summary = "유저 삭제")
    public ResponseEntity<Void> deleteUserById(@PathVariable final Long userId) {
        accountService.deleteUser(userId);
        return ResponseEntity.ok().build();
    }
}
