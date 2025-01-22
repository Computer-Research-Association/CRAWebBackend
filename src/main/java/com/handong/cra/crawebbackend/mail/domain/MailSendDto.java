package com.handong.cra.crawebbackend.mail.domain;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class MailSendDto {
    private String sendEmail;
    private MailCategory mailCategory;
    private String username;
}