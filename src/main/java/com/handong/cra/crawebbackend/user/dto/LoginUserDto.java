package com.handong.cra.crawebbackend.user.dto;

import com.handong.cra.crawebbackend.user.domain.User;
import com.handong.cra.crawebbackend.user.domain.UserRoleEnum;
import com.handong.cra.crawebbackend.user.domain.UserRoleSet;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class LoginUserDto {
    private String username;
    private String password;
    private UserRoleSet roles;

    public LoginUserDto(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.roles = user.getRoles();
    }

    public static LoginUserDto from(User user) {
        return new LoginUserDto(user);
    }
}
