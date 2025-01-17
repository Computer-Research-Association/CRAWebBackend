package com.handong.cra.crawebbackend.user.dto.response;

import com.handong.cra.crawebbackend.user.dto.UpdateUserDto;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ResUpdateUserDto {
    String imgUrl;
    //TODO : 수정할 데이터 추가

    public ResUpdateUserDto(UpdateUserDto updateUserDto) {
        this.imgUrl = updateUserDto.getImgUrl();
    }

    public static ResUpdateUserDto from(UpdateUserDto updateUserDto) {
        return new ResUpdateUserDto(updateUserDto);
    }


}
