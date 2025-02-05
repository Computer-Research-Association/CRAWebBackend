package com.handong.cra.crawebbackend.user.dto.request;


import lombok.*;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ReqUpdateUserDto {
    private String name;
//    private
    private String imgUrl;
    //TODO : 수정할 데이터 추가
}
