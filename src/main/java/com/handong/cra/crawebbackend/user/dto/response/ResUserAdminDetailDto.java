package com.handong.cra.crawebbackend.user.dto.response;

import com.handong.cra.crawebbackend.user.domain.UserRoleEnum;
import com.handong.cra.crawebbackend.user.domain.UserRoleSet;
import com.handong.cra.crawebbackend.user.dto.UserDetailDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ResUserAdminDetailDto extends ResUserDetailDto {
    private Long userId;
    private UserRoleSet roles;


    public ResUserAdminDetailDto(UserDetailDto userDetailDto) {
        super(userDetailDto);
        this.userId = userDetailDto.getId();
        roles = userDetailDto.getRoles();
    }

    public static ResUserAdminDetailDto from(UserDetailDto UserDetailDto) {
        return new ResUserAdminDetailDto(UserDetailDto);
    }
}
