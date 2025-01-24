package com.handong.cra.crawebbackend.account.service;

import com.handong.cra.crawebbackend.account.domain.ManageToken;
import com.handong.cra.crawebbackend.account.domain.ManageTokenCategory;
import com.handong.cra.crawebbackend.account.dto.CodeDto;
import com.handong.cra.crawebbackend.account.repository.ManageTokenRepository;
import com.handong.cra.crawebbackend.exception.account.AccountCodeExpiredException;
import com.handong.cra.crawebbackend.exception.account.AccountCodeNotFoundException;
import com.handong.cra.crawebbackend.exception.user.UserNotFoundException;
import com.handong.cra.crawebbackend.mail.domain.MailCategory;
import com.handong.cra.crawebbackend.mail.domain.MailSendDto;
import com.handong.cra.crawebbackend.mail.service.MailService;
import com.handong.cra.crawebbackend.user.domain.User;
import com.handong.cra.crawebbackend.user.repository.UserRepository;
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
    private final UserRepository userRepository;
    private final MailService mailService;

    public List<CodeDto> generateSignupCodes(Short length) {
        List<CodeDto> codeDtos = new ArrayList<>();
        for (int i = 0; i < length; i++) codeDtos.add(generateToken(ManageTokenCategory.SIGNUP));
        return codeDtos;
    }

    private LocalDateTime getExpireDate(LocalDateTime now) { // TODO : refactor
        return now.plusHours(24);
    }

    public void codeValidCheck(String code, ManageTokenCategory manageTokenCategory) {
        ManageToken manageToken = manageTokenRepository.findByCode(code);

        if (manageToken == null) // 없음
            throw new AccountCodeNotFoundException();
        else if (manageToken.getManageTokenCategory() != manageTokenCategory) // 코드는 있는데, 카테고리가 다름
            throw new AccountCodeNotFoundException();
        else if (!manageToken.getExpireDate().isAfter(LocalDateTime.now())) // 만료됨
            throw new AccountCodeExpiredException();
    }

    private CodeDto generateToken(ManageTokenCategory manageTokenCategory) {
        LocalDateTime expireDate = getExpireDate(LocalDateTime.now());
        String uuid = UUID.randomUUID().toString();
        CodeDto codeDto = CodeDto.of(uuid, manageTokenCategory, expireDate);
        return CodeDto.of(manageTokenRepository.save(ManageToken.from(codeDto)));
    }

    public void requestChangingPassword(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) throw new UserNotFoundException();

        // 토큰 발행
        CodeDto codeDto = generateToken(ManageTokenCategory.PASSWORD_CHANGE);

        String passwordChangeUrl = /*TODO : url here*/ "?code=" + codeDto.getCode();

        // 이메일 전송
        MailSendDto mailSendDto = MailSendDto.builder()
                .sendEmail(user.getEmail())
//                .url(passwordChangeUrl)
                .mailCategory(MailCategory.PASSWORD_EMAIL)
                .username(user.getUsername())
                .build();

        mailService.sendMimeMessage(mailSendDto);
    }

    public Boolean validUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) return true;
        else return false;
    }
}
