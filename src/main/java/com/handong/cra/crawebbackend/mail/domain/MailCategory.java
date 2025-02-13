package com.handong.cra.crawebbackend.mail.domain;

import lombok.Getter;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.nio.charset.Charset;

@Getter
public enum MailCategory {

    WELCOME_EMAIL("[CRA] 가입을 환영합니다!", "templates/welcome-email.html"), // 가입 환영
    PASSWORD_EMAIL("[CRA] 비밀번호 재설정 안내", "templates/password-email.html"), // 비밀번호 찾기
    EMAILVALID_EMAIL("[CRA] 이메일 인증 코드 전송", "templates/email_valid_check-email.html"), // 비밀번호 찾기
    DORMANTUSER_EMAIL("[CRA] 휴면계정 전환 안내", "templates/dormant-user-email.html"), // 비밀번호 찾기
    DELETEUSER_EMAIL("[CRA] 계정 삭제 안내", "templates/user-delete-email.html"); // 비밀번호 찾기

    private final String title;
    private final ClassPathResource template;

    MailCategory(String title, String templateLocation) {
        this.title = title;
        this.template = new ClassPathResource(templateLocation);
    }
}