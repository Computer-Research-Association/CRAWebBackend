package com.handong.cra.crawebbackend.mail.domain;


import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EmailMessage {
    private String to;

    private String subject;

    private String message;
}
