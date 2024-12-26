package com.handong.cra.crawebbackend.test.controller;


import com.handong.cra.crawebbackend.user.domain.User;
import com.handong.cra.crawebbackend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class TestController {
    private final UserRepository userRepository;

    @GetMapping
    public String test() {
        return "Hello World";
    }

    @PostMapping("/user")
    public void user() {
        User user = new User("테스트", "testid", "test@test.com", "테스트", 22200123L, "24-2");
        userRepository.save(user);
    }
}
