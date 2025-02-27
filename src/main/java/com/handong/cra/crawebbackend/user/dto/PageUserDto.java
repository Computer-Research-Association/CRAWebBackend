package com.handong.cra.crawebbackend.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PageUserDto {
    private List<UserDetailDto> userDetailDtos;
    private Integer totalPages;

    public static PageUserDto of(List<UserDetailDto> userDetailDtos, int totalPages) {
        return  new PageUserDto(userDetailDtos,totalPages);
    }
}
