package com.handong.cra.crawebbackend.account.service;

import com.handong.cra.crawebbackend.account.domain.ManageToken;
import com.handong.cra.crawebbackend.account.domain.ManageTokenCategory;
import com.handong.cra.crawebbackend.account.dto.CodeDto;
import com.handong.cra.crawebbackend.account.dto.FindUsernameDto;
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
import com.handong.cra.crawebbackend.user.dto.PageUserDataDto;
import com.handong.cra.crawebbackend.user.dto.PageUserDto;
import com.handong.cra.crawebbackend.user.dto.UserDetailDto;
import com.handong.cra.crawebbackend.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final ManageTokenRepository manageTokenRepository;
    private final UserRepository userRepository;
    private final MailService mailService;

    @Value("${site.frontend.url}")
    private String frontUrl;

    @Override
    public List<CodeDto> generateSignupCodes(final Short length) {
        final List<CodeDto> codeDtos = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            codeDtos.add(generateToken(ManageTokenCategory.SIGNUP));
        }
        return codeDtos;
    }


    @Override
    public Long codeValidCheck(final String code, final ManageTokenCategory manageTokenCategory) {
        final ManageToken manageToken = manageTokenRepository.findByCode(code);
        if (manageToken == null) {// 없음
            throw new AccountCodeNotFoundException();
        }
        if (manageToken.getManageTokenCategory() != manageTokenCategory) { // 코드는 있는데, 카테고리가 다름
            throw new AccountCodeNotFoundException();
        }
        if (!manageToken.getExpireDate().isAfter(LocalDateTime.now())) { // 만료됨
            throw new AccountCodeExpiredException();
        }
        final Long userId = manageToken.getUserId();
        // passed!
        manageTokenRepository.delete(manageToken);
        return Objects.requireNonNullElse(userId, 0L);

    }

    private CodeDto generateToken(final ManageTokenCategory manageTokenCategory) {
        final LocalDateTime expireDate = getExpireDate(LocalDateTime.now());
        final String uuid = UUID.randomUUID().toString();
        final CodeDto codeDto = CodeDto.of(uuid, manageTokenCategory, expireDate);
        return CodeDto.of(manageTokenRepository.save(ManageToken.from(codeDto)));
    }


    @Override
    public void requestChangingPassword(final String username) {
        final User user = userRepository.findByUsername(username);
        final CodeDto codeDto = generateToken(ManageTokenCategory.PASSWORD_CHANGE, user.getId()); // 토큰 발행
        final String passwordChangeUrl = frontUrl + "/pwsearch/reset?code=" + codeDto.getCode();
        final MailSendDto mailSendDto = MailSendDto.builder() // 이메일 DTO 생성
                .sendEmail(user.getEmail())
                .url(passwordChangeUrl)
                .mailCategory(MailCategory.PASSWORD_EMAIL)
                .username(user.getUsername())
                .build();
        mailService.sendMimeMessage(mailSendDto); // 이메일 전송
    }

    @Override
    public Boolean validUsername(final String username) {
        final User user = userRepository.findByUsername(username);
        return (user == null);
    }

    @Override
    public String findUsername(final FindUsernameDto findUsernameDto) {
        final User user = userRepository.findByNameAndStudentIdAndEmail(
                findUsernameDto.getName(),
                findUsernameDto.getStudentId(),
                findUsernameDto.getEmail()
        );

        if (user == null) {
            throw new UserNotFoundException();
        }
        return user.getUsername();
    }

    @Override
    public void emailValidCheck(final String email) {
        final User user = userRepository.findByEmail(email);
        if (user != null) {
            throw new AccountEmailAlreadyExistsException(); // 이미 존제하는 이메일
        }
        final CodeDto codeDto = generateToken(ManageTokenCategory.EMAIL_VALID);
        final String code = codeDto.getCode();
        final MailSendDto mailSendDto = MailSendDto.builder().mailCategory(MailCategory.EMAILVALID_EMAIL)
                .code(code)
                .sendEmail(email)
                .build();
        mailService.sendMimeMessage(mailSendDto);
    }

    @Override
    @Transactional
    public void addUserAuthById(final Long userId, final UserRoleEnum userRoleEnum) {
        final User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        user.getRoles().addRole(userRoleEnum);
    }

    @Override
    @Transactional
    public void removeUserAuthById(final Long userId, final UserRoleEnum userRoleEnum) {
        final User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        user.getRoles().removeRole(userRoleEnum);
    }

    @Override
    public List<UserDetailDto> findUsersByName(final String name) {
        final List<User> users = userRepository.findAllByName(name);
        return users.stream().map(UserDetailDto::from).toList();
    }

    @Override
    public PageUserDto getPaginationUser(final PageUserDataDto pageUserDataDto) {
        final Page<User> users = userRepository.findAll(PageRequest.of(Math.toIntExact(pageUserDataDto.getPage()), pageUserDataDto.getPerPage()));
        return PageUserDto.of(users.stream().map(UserDetailDto::from).toList(), users.getTotalPages());
    }

    @Override
    @Transactional
    public Boolean activeAccount(final Long userId) {
        final User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        user.activeUser();
        return true;
    }

    @Override
    @Transactional
    public Boolean deleteUser(final Long userId) {
        userRepository.deleteById(userId);
        return true;
    }

    private CodeDto generateToken(final ManageTokenCategory manageTokenCategory, final Long userId) {
        final LocalDateTime expireDate = getExpireDate(LocalDateTime.now());
        final String uuid = UUID.randomUUID().toString();
        final CodeDto codeDto = CodeDto.of(uuid, manageTokenCategory, userId, expireDate);
        return CodeDto.of(manageTokenRepository.save(ManageToken.from(codeDto)));
    }

    private LocalDateTime getExpireDate(final LocalDateTime now) {
        return now.plusHours(24);
    }
}
