package com.handong.cra.crawebbackend.mail.controller;

import com.handong.cra.crawebbackend.mail.domain.MailCategory;
import com.handong.cra.crawebbackend.mail.domain.MailSendDto;
import com.handong.cra.crawebbackend.mail.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mail")
@RequiredArgsConstructor
public class MailController {

    private final MailService mailService;

    @GetMapping("/html")
    public ResponseEntity<Void> sendMimeMessage() {
//        MailSendDto mailSendDto = MailSendDto.builder()
//                .mailCategory(MailCategory.PASSWORD_EMAIL)
//                .username("Whal3")
//                .build();
//        mailService.sendMimeMessage(mailSendDto);
        return ResponseEntity.ok().build();
    }
}