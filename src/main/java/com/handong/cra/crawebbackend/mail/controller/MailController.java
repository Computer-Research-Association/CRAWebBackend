package com.handong.cra.crawebbackend.mail.controller;

import com.handong.cra.crawebbackend.mail.domain.MailDto;
import com.handong.cra.crawebbackend.mail.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mail")
@RequiredArgsConstructor
public class MailController {

    private final MailService mailService;

    @GetMapping("/simple")
    public ResponseEntity<Void> sendSimpleMailMessage(@RequestBody MailDto mailDto) {
        mailService.sendSimpleMailMessage(mailDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/html")
    public ResponseEntity<Void> sendMimeMessage(@RequestBody MailDto mailDto) {
        mailService.sendMimeMessage(mailDto);
        return ResponseEntity.ok().build();
    }
}