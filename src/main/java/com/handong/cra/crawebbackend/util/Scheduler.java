package com.handong.cra.crawebbackend.util;

import com.handong.cra.crawebbackend.user.domain.User;
import com.handong.cra.crawebbackend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class Scheduler {
    private final UserRepository userRepository;




    // 유저는 DB상 지워지기 떄문에, deleted 를 휴면 계정 처리로 사용
    @Scheduled(cron = "0 0 0 * * ?") // 매일 00:00 에 실행
    public void findDormantUserAndDelete(){
        LocalDateTime aYearBefore = LocalDateTime.now().minusYears(1);
        List<User> users= userRepository.findAllByLastLoginAtBeforeAndDeletedFalse(aYearBefore);
        for (User user: users){
            user.setLastLoginAt(LocalDateTime.now()); // 휴면 계정 처리 1년 이후 삭제
            user.delete(); // 휴면 계정 처리
            // TODO: 메일 보내기
        }

        // 유저 데이터 삭제
        users= userRepository.findAllByLastLoginAtBeforeAndDeletedTrue(aYearBefore);
        userRepository.deleteAll(users);
        // TODO: 메일 보내기
    }

}
