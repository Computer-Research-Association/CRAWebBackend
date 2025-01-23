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

    public void sendMimeMessage(MailSendDto mailSendDto) { // TODO exception 처리?
//        if (!mailSendDto.getSendEmail().contains("@")){
//            throw new
//        }


        MimeMessage mimeMessage = javaMailSender.createMimeMessage();


        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            // set email to send
            mimeMessageHelper.setTo(mailSendDto.getSendEmail());

            // set title
            mimeMessageHelper.setSubject(mailSendDto.getMailCategory().getTitle());

            // set content
            String content = mailSendDto.getMailCategory().getTemplate().getContentAsString(Charset.defaultCharset());

            // change to username
            if (mailSendDto.getUsername()!=null)
                content = content.replace("#username", mailSendDto.getUsername());
            if (mailSendDto.getUrl() != null)
                content = content.replace("#url", mailSendDto.getUrl());

            mimeMessageHelper.setText(content, true);

            // send
            javaMailSender.send(mimeMessage);

            log.info("send Email to {}, category = {}", mailSendDto.getSendEmail(), mailSendDto.getMailCategory().toString());
        } catch (Exception e) {
            log.error("Fail sending Email!");
            throw new RuntimeException(e);
        }
    }

}