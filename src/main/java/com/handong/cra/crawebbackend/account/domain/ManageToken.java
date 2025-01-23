package com.handong.cra.crawebbackend.account.domain;

import com.handong.cra.crawebbackend.account.dto.CodeDto;
import com.handong.cra.crawebbackend.common.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Entity
public class ManageToken extends BaseEntity {
    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private ManageTokenCategory manageTokenCategory;

    @Column(nullable = false)
    private LocalDateTime expireDate;


    // 패스워드 변경시 변경할 유저의 아이디
    private Long userId;

    public ManageToken(CodeDto codeDto) {
        this.code = codeDto.getCode();
        this.manageTokenCategory = codeDto.getManageTokenCategory();
        this.expireDate = codeDto.getExpireAt();
        this.userId = codeDto.getUserId();
    }


    public static ManageToken from(CodeDto codeDto) {
        return new ManageToken(codeDto);
    }
}
