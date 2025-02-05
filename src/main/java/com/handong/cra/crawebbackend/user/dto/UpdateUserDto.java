package com.handong.cra.crawebbackend.user.dto;

import com.handong.cra.crawebbackend.user.domain.User;
import com.handong.cra.crawebbackend.user.dto.request.ReqUpdateUserDto;
import com.handong.cra.crawebbackend.user.dto.response.ResUpdateUserDto;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UpdateUserDto {
    private Long userId;
    private String imgUrl;
    //TODO : 수정할 데이터 추가


    public UpdateUserDto (User user){
        this.imgUrl = user.getImgUrl();
    }

    public UpdateUserDto (Long userId, ReqUpdateUserDto reqUpdateUserDto){
        this.userId = userId;
        this.imgUrl = reqUpdateUserDto.getImgUrl();
    }

    public static UpdateUserDto from(User user){
        return new UpdateUserDto(user);
    }
    public static UpdateUserDto from(Long userId, ReqUpdateUserDto reqUpdateUserDto){
        return new UpdateUserDto(userId, reqUpdateUserDto);
    }

}
