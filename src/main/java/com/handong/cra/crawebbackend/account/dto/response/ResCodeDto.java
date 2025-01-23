package com.handong.cra.crawebbackend.account.dto.response;


import com.handong.cra.crawebbackend.account.domain.ManageTokenCategory;
import com.handong.cra.crawebbackend.account.dto.CodeDto;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResCodeDto {
    private String code;
    private ManageTokenCategory manageTokenCategory;
    private LocalDateTime createdAt;

    public ResCodeDto(CodeDto codeDto) {
        this.code = codeDto.getCode();
        this.manageTokenCategory = codeDto.getManageTokenCategory();
        this.createdAt = codeDto.getCreatedAt();
    }


    public static ResCodeDto from(CodeDto codeDto) {
        return new ResCodeDto(codeDto);
    }
}
