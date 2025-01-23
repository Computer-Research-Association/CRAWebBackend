package com.handong.cra.crawebbackend.mail.domain;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class MailSendDto {

    @NotNull
    private String sendEmail;
    @NotNull
    private MailCategory mailCategory;

    // datas
    private String username;
    private String url;
}