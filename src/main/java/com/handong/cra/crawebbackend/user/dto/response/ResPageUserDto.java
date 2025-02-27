package com.handong.cra.crawebbackend.user.dto.response;

import com.handong.cra.crawebbackend.user.dto.PageUserDto;
import com.handong.cra.crawebbackend.user.dto.UserDetailDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ResPageUserDto {
    private List<ResUserAdminDetailDto> resUserAdminDetailDtos;
    private Integer totalPages;

    public static ResPageUserDto from(PageUserDto pageUserDto) {
        return new ResPageUserDto(pageUserDto.getUserDetailDtos().stream().map(ResUserAdminDetailDto::from).toList(), pageUserDto.getTotalPages());
    }
}
