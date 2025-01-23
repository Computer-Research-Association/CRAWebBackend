package com.handong.cra.crawebbackend.account.service;

import com.handong.cra.crawebbackend.account.domain.ManageToken;
import com.handong.cra.crawebbackend.account.domain.ManageTokenCategory;
import com.handong.cra.crawebbackend.account.dto.CodeDto;
import com.handong.cra.crawebbackend.account.repository.ManageTokenRepository;
import com.handong.cra.crawebbackend.exception.account.AccountCodeExpiredException;
import com.handong.cra.crawebbackend.exception.account.AccountCodeNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final ManageTokenRepository manageTokenRepository;

    public List<CodeDto> generateSignupCodes(Short length) {
        List<CodeDto> codeDtos = new ArrayList<>();
        LocalDateTime expireDate = getExpireDate(LocalDateTime.now());
        for (int i = 0; i < length; i++) {
            String uuid = UUID.randomUUID().toString();
            CodeDto codeDto = CodeDto.of(uuid, ManageTokenCategory.SIGNUP, expireDate);
            codeDto = CodeDto.of(manageTokenRepository.save(ManageToken.from(codeDto)));
            codeDtos.add(codeDto);

        }
        return codeDtos;
    }

    private LocalDateTime getExpireDate(LocalDateTime now) { // TODO : refactor
        return now.plusHours(24);
    }

    public void signupCodeValidCheck(String code) {
        ManageToken manageToken = manageTokenRepository.findByCode(code);

        // 없음
        if (manageToken == null)
            throw new AccountCodeNotFoundException();

        // 코드는 있는데, 카테고리가 다름
        else if (manageToken.getManageTokenCategory() != ManageTokenCategory.PASSWORD_CHANGE)
            throw new AccountCodeNotFoundException();

        // 만료됨
        else if (!manageToken.getExpireDate().isAfter(LocalDateTime.now()))
            throw new AccountCodeExpiredException();

    }
}
