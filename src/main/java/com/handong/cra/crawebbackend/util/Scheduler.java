package com.handong.cra.crawebbackend.util;

import com.handong.cra.crawebbackend.account.domain.ManageToken;
import com.handong.cra.crawebbackend.account.repository.ManageTokenRepository;
import com.handong.cra.crawebbackend.mail.domain.MailCategory;
import com.handong.cra.crawebbackend.mail.domain.MailSendDto;
import com.handong.cra.crawebbackend.mail.service.MailService;
import com.handong.cra.crawebbackend.user.domain.User;
import com.handong.cra.crawebbackend.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class Scheduler {
    private final UserRepository userRepository;
    private final MailService mailService;
    private final ManageTokenRepository manageTokenRepository;


    // 주기적으로 토큰 DB 정리
    @Scheduled(cron = "0 0 12 * * *", zone = "Asia/Seoul")
    @Transactional
    protected void deleteTokens() {
        int count = 0;
        log.info("============================================");
        log.info("[TOKEN CLEANUP] Scheduled task started.");
        List<ManageToken> manageTokens = manageTokenRepository.findAll();
        for (ManageToken manageToken : manageTokens) {
            if (manageToken.getExpireDate().isBefore(LocalDateTime.now())) {
                manageTokenRepository.delete(manageToken);
                count++;
            }
        }

        log.info("[TOKEN CLEANUP] {} expired tokens deleted.", count);
        log.info("[TOKEN CLEANUP] Scheduled task completed.");
        log.info("============================================");
    }


    // 유저는 DB상 지워지기 떄문에, deleted 를 휴면 계정 처리로 사용
    @Scheduled(cron = "0 0 0 * * ?", zone = "Asia/Seoul") // 매일 00:00 에 실행
    @Transactional
    protected void findDormantUserAndDelete() {
        int dormantCount = 0;
        int deleteCount = 0;
        log.info("============================================");
        log.info("[USER CLEANUP] Dormant user check started.");
        LocalDateTime aYearBefore = LocalDateTime.now().minusYears(1);
        List<User> users = userRepository.findAllByLastLoginAtBeforeAndDeletedFalse(aYearBefore);


        for (User user : users) {
            dormantCount++;
            user.setLastLoginAt(LocalDateTime.now()); // 휴면 계정 처리 1년 이후 삭제
            user.delete(); // 휴면 계정 처리
            MailSendDto mailSendDto = MailSendDto.builder().
                    username(user.getUsername())
                    .sendEmail(user.getEmail())
                    .mailCategory(MailCategory.DORMANTUSER_EMAIL)
                    .build();
            mailService.sendMimeMessage(mailSendDto);
        }
        log.info("[USER CLEANUP] {} users marked as dormant.", dormantCount);


        users = userRepository.findAllByLastLoginAtBeforeAndDeletedTrue(aYearBefore);

        // 유저 데이터 삭제
        userRepository.deleteAll(users);
        for (User user : users) {
            deleteCount++;
            MailSendDto mailSendDto = MailSendDto.builder()
                    .username(user.getUsername())
                    .sendEmail(user.getEmail())
                    .mailCategory(MailCategory.DELETEUSER_EMAIL)
                    .build();
            mailService.sendMimeMessage(mailSendDto);
        }

        log.info("[USER CLEANUP] {} dormant users deleted.", deleteCount);

        log.info("[USER CLEANUP] Scheduled task completed.");
        log.info("============================================");
    }
}
