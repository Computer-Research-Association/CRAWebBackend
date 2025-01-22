package com.handong.cra.crawebbackend.mail.service;

import com.handong.cra.crawebbackend.mail.domain.MailDto;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.core.io.Resource;

import java.nio.charset.Charset;

@Service
@Slf4j
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender javaMailSender;

    @Value("classpath:templates/password-email.html")
    private Resource emailTemplate;

    public void sendSimpleMailMessage(MailDto mailDto) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(mailDto.getEmail());
            message.setTo("email@email.com");
            message.setSubject(mailDto.getName());
            message.setText(mailDto.getMessage() + " "+ mailDto.getEmail());
            javaMailSender.send(message);
            log.info("메일 발송 성공!");
        } catch (Exception e) {
            log.info("메일 발송 실패!");
            throw new RuntimeException(e);
        }
    }

    public void sendMimeMessage(MailDto mailDto) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try{
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");

            // 메일을 받을 수신자 설정
            mimeMessageHelper.setTo("email@email.com");
            // 메일의 제목 설정
            mimeMessageHelper.setSubject("[CRA] 비밀번호 재설정 안내");

            // html 문법 적용한 메일의 내용
            String content = emailTemplate.getContentAsString(Charset.defaultCharset());

            // 메일의 내용 설정
            mimeMessageHelper.setText(content, true);

            javaMailSender.send(mimeMessage);

            log.info("메일 발송 성공!");
        } catch (Exception e) {
            log.info("메일 발송 실패!");
            throw new RuntimeException(e);
        }
    }

}