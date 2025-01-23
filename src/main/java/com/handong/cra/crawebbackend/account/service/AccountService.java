package com.handong.cra.crawebbackend.account.service;

import com.handong.cra.crawebbackend.account.domain.ManageTokenCategory;
import com.handong.cra.crawebbackend.account.dto.CodeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountService {


    public List<CodeDto> generateSignupCodes(Short length) {
        List<CodeDto> codeDtos = new ArrayList<>();
        LocalDateTime expireDate = getExpireDate(LocalDateTime.now());
        for(int i = 0 ;i < length ; i++){
            String uuid = UUID.randomUUID().toString();
            CodeDto codeDto = new CodeDto(uuid, ManageTokenCategory.SIGNUP, expireDate,null, );

        }



    }

    private LocalDateTime getExpireDate(LocalDateTime now) { // TODO : refactor
        return now.plusHours(24);
    }
}
