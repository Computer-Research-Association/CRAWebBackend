package com.handong.cra.crawebbackend.user.dto;

import com.handong.cra.crawebbackend.user.domain.User;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CacheUserDto {
    private User user;

    public static CacheUserDto from(User user) {
        return new CacheUserDto(user);
    }
}
