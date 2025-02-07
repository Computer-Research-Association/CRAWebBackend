package com.handong.cra.crawebbackend.user.dto.request;


import lombok.*;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ReqUpdateUserDto {
    private Long id;
    private String name;
    private String email;
    private Long studentId;
    private String term;
    private String githubId;

}
