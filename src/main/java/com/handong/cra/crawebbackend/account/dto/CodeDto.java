package com.handong.cra.crawebbackend.account.dto;

import com.handong.cra.crawebbackend.account.domain.ManageToken;
import com.handong.cra.crawebbackend.account.domain.ManageTokenCategory;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CodeDto {
    private String code;
    private ManageTokenCategory manageTokenCategory;
    private Long userId;
    private LocalDateTime createdAt;
    private LocalDateTime expireAt;

    public CodeDto(String code, ManageTokenCategory manageTokenCategory, Long userId, LocalDateTime expireAt) {
        this.code = code;
        this.manageTokenCategory = manageTokenCategory;
        this.userId = userId;
        this.expireAt = expireAt;
    }

    public CodeDto(ManageToken manageToken) {
        this.code = manageToken.getCode();
        this.manageTokenCategory = manageToken.getManageTokenCategory();
        this.userId = manageToken.getUserId();
        this.createdAt = manageToken.getCreatedAt();
        this.expireAt = manageToken.getExpireDate();
    }

    public static CodeDto of(String code, ManageTokenCategory manageTokenCategory, Long userId, LocalDateTime expireAt) {
        return new CodeDto(code, manageTokenCategory, userId, expireAt);
    }

    public static CodeDto of(String code, ManageTokenCategory manageTokenCategory, LocalDateTime expireAt) {
        return new CodeDto(code, manageTokenCategory, null, expireAt);
    }

    public static CodeDto of(ManageToken manageToken) {
        return new CodeDto(manageToken);
    }
}