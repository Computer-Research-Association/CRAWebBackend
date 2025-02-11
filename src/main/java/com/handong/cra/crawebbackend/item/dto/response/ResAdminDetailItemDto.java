package com.handong.cra.crawebbackend.item.dto.response;

import com.handong.cra.crawebbackend.item.dto.DetailItemDto;
import com.handong.cra.crawebbackend.user.dto.response.ResUserDetailDto;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResAdminDetailItemDto extends ResDetailItemDto {
    private ResUserDetailDto resUserDetailDto;

    public ResAdminDetailItemDto(DetailItemDto detailItemDto) {
        super(detailItemDto);
        this.resUserDetailDto = ResUserDetailDto.from(detailItemDto.getUserDetailDto());
    }

    public static ResAdminDetailItemDto from(DetailItemDto detailItemDto) {
        return new ResAdminDetailItemDto(detailItemDto);
    }
}
