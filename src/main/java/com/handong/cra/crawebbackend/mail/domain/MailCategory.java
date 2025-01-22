package com.handong.cra.crawebbackend.mail.domain;
import lombok.Getter;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

@Getter
public enum MailCategory {

    WELCOME_EMAIL("[CRA] 비밀번호 재설정 안내", "classpath:templates/password-email.html"), // 가입 환영
    PASSWORD_EMAIL("[CRA] 가입을 환영합니다!", "classpath:templates/welcome-email.html"); // 비밀번호 찾기

    private final String title;
    private final Resource template;

    MailCategory(String title,String templateLocation){
        this.title = title;
        this.template = new ClassPathResource(templateLocation);
    }
}