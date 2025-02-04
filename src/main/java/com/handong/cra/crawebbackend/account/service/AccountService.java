package com.handong.cra.crawebbackend.account.service;

import com.handong.cra.crawebbackend.account.domain.ManageTokenCategory;
import com.handong.cra.crawebbackend.account.dto.CodeDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AccountService {

    public List<CodeDto> generateSignupCodes(Short length);

    public Long codeValidCheck(String code, ManageTokenCategory manageTokenCategory);

    public void requestChangingPassword(String username);

    public Boolean validUsername(String username);

    public String findUsername(Integer studentId, String name, String email);
}
