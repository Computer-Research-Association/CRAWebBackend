package com.handong.cra.crawebbackend.mail.domain;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MailDto {

    private int id;
    private String name;
    private String email;
    private String message;
}