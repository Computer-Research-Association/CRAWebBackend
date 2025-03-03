package com.handong.cra.crawebbackend.user.service;

import com.handong.cra.crawebbackend.user.dto.CacheUserDto;
import com.handong.cra.crawebbackend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtUserServiceImpl implements JwtUserService {
    private final UserRepository userRepository;

    @Override
    @Cacheable(value = "users", key = "#userId")
    public CacheUserDto getUserById(Long userId) {
        return CacheUserDto.from(userRepository.getUserById(userId));
    }
}
