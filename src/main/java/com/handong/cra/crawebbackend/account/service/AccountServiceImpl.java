package com.handong.cra.crawebbackend.account.service;

import com.handong.cra.crawebbackend.account.domain.ManageToken;
import com.handong.cra.crawebbackend.account.domain.ManageTokenCategory;
import com.handong.cra.crawebbackend.account.dto.CodeDto;
import com.handong.cra.crawebbackend.account.repository.ManageTokenRepository;
import com.handong.cra.crawebbackend.exception.account.AccountCodeExpiredException;
import com.handong.cra.crawebbackend.exception.account.AccountCodeNotFoundException;
import com.handong.cra.crawebbackend.exception.account.AccountEmailAlreadyExistsException;
import com.handong.cra.crawebbackend.exception.user.UserNotFoundException;
import com.handong.cra.crawebbackend.mail.domain.MailCategory;
import com.handong.cra.crawebbackend.mail.domain.MailSendDto;
import com.handong.cra.crawebbackend.mail.service.MailService;
import com.handong.cra.crawebbackend.user.domain.User;
import com.handong.cra.crawebbackend.user.domain.UserRoleEnum;
import com.handong.cra.crawebbackend.user.dto.UserDetailDto;
import com.handong.cra.crawebbackend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final ManageTokenRepository manageTokenRepository;
    private final UserRepository userRepository;
    private final MailService mailService;

    @Override
    public List<CodeDto> generateSignupCodes(Short length) {
        List<CodeDto> codeDtos = new ArrayList<>();
        for (int i = 0; i < length; i++) codeDtos.add(generateToken(ManageTokenCategory.SIGNUP));
        return codeDtos;
    }

    private LocalDateTime getExpireDate(LocalDateTime now) { // TODO : refactor
        return now.plusHours(24);
    }

    @Override
    public Long codeValidCheck(String code, ManageTokenCategory manageTokenCategory) {
        ManageToken manageToken = manageTokenRepository.findByCode(code);
        if (manageToken == null) // 없음
            throw new AccountCodeNotFoundException();
        else if (manageToken.getManageTokenCategory() != manageTokenCategory) // 코드는 있는데, 카테고리가 다름
            throw new AccountCodeNotFoundException();
        else if (!manageToken.getExpireDate().isAfter(LocalDateTime.now())) // 만료됨
            throw new AccountCodeExpiredException();

        Long userId = manageToken.getUserId();

        manageTokenRepository.delete(manageToken);

        if (userId != null) return userId;
        else return 0L;

        // passed!
    }

    private CodeDto generateToken(ManageTokenCategory manageTokenCategory) {
        LocalDateTime expireDate = getExpireDate(LocalDateTime.now());
        String uuid = UUID.randomUUID().toString();
        CodeDto codeDto = CodeDto.of(uuid, manageTokenCategory, expireDate);
        return CodeDto.of(manageTokenRepository.save(ManageToken.from(codeDto)));
    }

    // password
    private CodeDto generateToken(ManageTokenCategory manageTokenCategory, Long userId) {
        LocalDateTime expireDate = getExpireDate(LocalDateTime.now());
        String uuid = UUID.randomUUID().toString();
        CodeDto codeDto = CodeDto.of(uuid, manageTokenCategory, userId, expireDate);
        return CodeDto.of(manageTokenRepository.save(ManageToken.from(codeDto)));
    }

    @Override
    public void requestChangingPassword(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) throw new UserNotFoundException();

        // 토큰 발행
        CodeDto codeDto = generateToken(ManageTokenCategory.PASSWORD_CHANGE, user.getId());

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

    @Override
    public Boolean validUsername(String username) {
        User user = userRepository.findByUsername(username);
        return user == null;
    }

    @Override
    public String findUsername(String studentId, String name, String email) {
        User user = userRepository.findByNameAndStudentIdAndEmail(name, studentId, email);

        if (user == null) throw new UserNotFoundException();

        return user.getUsername();
    }

    @Override
    public void emailValidCheck(String email) {

        User user = userRepository.findByEmail(email);
        if (user != null) throw new AccountEmailAlreadyExistsException();


        log.info(ManageTokenCategory.EMAIL_VALID.toString());
        CodeDto codeDto = generateToken(ManageTokenCategory.EMAIL_VALID);
        String code = codeDto.getCode();

        MailSendDto mailSendDto = MailSendDto.builder().mailCategory(MailCategory.EMAILVALID_EMAIL)
                .code(code)
                .sendEmail(email)
                .build();

        mailService.sendMimeMessage(mailSendDto);
    }

    @Override
    public List<UserDetailDto> getUsersByEntranceYear(String year, String term) {
        List<UserDetailDto> userDetailDtos = new ArrayList<>();
        // 기수로 찾기

        if (year.isEmpty()) {
            List<User> users = userRepository.findAllByTerm(term);
            for (User user: users) userDetailDtos.add(UserDetailDto.from(user));
        }
        // 학번으로 찾기
        else if (term.isEmpty()) {
            List<User> users = userRepository.findByStudentCodeNative(year);
            for (User user: users) userDetailDtos.add(UserDetailDto.from(user));
        }
        return userDetailDtos;
    }

    @Override
    public void updateUserAuthById(Long userId, UserRoleEnum userRoleEnum) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        user.getRoles().addRole(userRoleEnum);

    }

}
