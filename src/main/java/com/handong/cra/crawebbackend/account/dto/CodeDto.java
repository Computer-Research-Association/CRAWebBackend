package com.handong.cra.crawebbackend.account.dto;

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
}