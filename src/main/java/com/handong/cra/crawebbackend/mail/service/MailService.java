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

    public void sendMimeMessage(MailSendDto mailSendDto) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");

            // set email to send
            mimeMessageHelper.setTo(mailSendDto.getSendEmail());

            // set title
            mimeMessageHelper.setSubject(mailSendDto.getMailCategory().getTitle());

            // set content
            String content = mailSendDto.getMailCategory().getTemplate().getContentAsString(Charset.defaultCharset());

            // change to username
            content = content.replace("#username", mailSendDto.getUsername());

            mimeMessageHelper.setText(content, true);

            // send
            javaMailSender.send(mimeMessage);

            log.info("send Email to {}, category = {}", mailSendDto.getSendEmail(), mailSendDto.getMailCategory().toString());
        } catch (Exception e) {
            log.info("메일 발송 실패!");
            throw new RuntimeException(e);
        }
    }

}