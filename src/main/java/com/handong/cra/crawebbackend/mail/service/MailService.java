package com.handong.cra.crawebbackend.mail.service;

import com.handong.cra.crawebbackend.mail.domain.MailSendDto;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;

@Service
@Slf4j
@RequiredArgsConstructor
public class MailService {
    private final JavaMailSender javaMailSender;

    public void sendMimeMessage(final MailSendDto mailSendDto) { // TODO : exception 처리?
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            mimeMessageHelper.setTo(mailSendDto.getSendEmail()); // set email to send
            mimeMessageHelper.setSubject(mailSendDto.getMailCategory().getTitle()); // set title
            String content = mailSendDto.getMailCategory().getTemplate()
                    .getContentAsString(Charset.defaultCharset()); // set content
            if (mailSendDto.getUsername() != null) { // change to username
                content = content.replace("#username", mailSendDto.getUsername());
            }
            if (mailSendDto.getUrl() != null) { // set URLS
                content = content.replace("#url", mailSendDto.getUrl());
            }
            if (mailSendDto.getCode() != null) { // set token code
                content = content.replace("#verification_code", mailSendDto.getCode());
            }
            mimeMessageHelper.setText(content, true);
            javaMailSender.send(mimeMessage); // send
        } catch (Exception e) {
            log.error("Fail sending Email!");
            throw new RuntimeException(e);
        }
    }
}