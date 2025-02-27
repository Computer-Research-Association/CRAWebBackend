package com.handong.cra.crawebbackend.account.controller;


import com.handong.cra.crawebbackend.account.dto.response.ResCodeDto;
import com.handong.cra.crawebbackend.account.service.AccountService;
import com.handong.cra.crawebbackend.exception.board.PageSizeLimitExceededException;
import com.handong.cra.crawebbackend.user.domain.UserRoleEnum;
import com.handong.cra.crawebbackend.user.dto.PageUserDataDto;
import com.handong.cra.crawebbackend.user.dto.response.ResPageUserDto;
import com.handong.cra.crawebbackend.user.dto.response.ResUserAdminDetailDto;
import com.handong.cra.crawebbackend.user.repository.UserRepository;
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
    private final UserRepository userRepository;


    @Value("${spring.data.page.MAX_PER_PAGE}")
    private Integer MAX_PAGE_SIZE;

    @GetMapping("/code/signup") // 가입 코드 생성
    public ResponseEntity<List<ResCodeDto>> generateSignupCodes(@RequestParam Short length) {
        return ResponseEntity.ok(accountService.generateSignupCodes(length).stream().map(ResCodeDto::from).toList());
    }

//    @GetMapping("/users")
//    public ResponseEntity<List<ResUserDetailDto>> getUsersByEntranceYear(@RequestParam(required = false, defaultValue = "") String year, @RequestParam(required = false, defaultValue = "") String term) {
//        if (year.isEmpty() && term.isEmpty()) throw new RuntimeException("test");
//        return ResponseEntity.ok(accountService.getUsersByEntranceYear(year, term).stream().map(ResUserDetailDto::from).toList());
//    }

    @GetMapping("/users/page/{page}")
    public ResponseEntity<ResPageUserDto> getPaginationUser(
            @PathVariable Long page, // 0부터 시작
            @RequestParam(required = false, defaultValue = "0") Integer perPage
    ) {
        if (perPage > MAX_PAGE_SIZE) throw new PageSizeLimitExceededException();
        return ResponseEntity.ok(ResPageUserDto.from(accountService.getPaginationUser(PageUserDataDto.builder().page(page).perPage(perPage).build())));
    }


    @GetMapping("/users/search")
    public ResponseEntity<List<ResUserAdminDetailDto>> findUsersByName
            (@RequestParam(required = false, defaultValue = "") String name) {
        return ResponseEntity.ok(accountService.findUsersByName(name).stream().map(ResUserAdminDetailDto::from).toList());
    }

    // TODO : admin을 뺴야 하는 경우 ?
    @PutMapping("/users/{userId}")
    public ResponseEntity<Void> updateUserAuthById(@PathVariable Long userId, @RequestParam Integer authOption) {
        accountService.updateUserAuthById(userId, UserRoleEnum.values()[authOption]);
        return ResponseEntity.ok().build();
    }

    //TODO 휴면 해제


    @DeleteMapping("/users/{userId}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long userId) {

        // 구현중
        return ResponseEntity.ok().build();
    }
}
